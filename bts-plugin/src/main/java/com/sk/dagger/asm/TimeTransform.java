package com.sk.dagger.asm;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.sk.dagger.bts_plugin.HelloExtension;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

public class TimeTransform extends Transform {
    //    private Map<String, File> modifyMap = new HashMap<>();
    private static final String NAME = "TimeTransform";
    private final Logger mLogger;
    public Project mProject;

    public TimeTransform(Project project) {
        mProject = project;
        mLogger = mProject.getLogger();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 指Transform要操作内容的范围，官方文档Scope有7种类型：
     * <p>
     * EXTERNAL_LIBRARIES        只有外部库
     * PROJECT                   只有项目内容
     * PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY             只提供本地或远程依赖项
     * SUB_PROJECTS              只有子项目。
     * SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)。
     * TESTED_CODE               由当前变量(包括依赖项)测试的代码
     * SCOPE_FULL_PROJECT        整个项目
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        // inputs中是传过来的输入流，其中有两种格式，一种是jar包格式一种是目录格式。
        Collection<TransformInput> inputs = transformInvocation.getInputs();

        // 获取到输出目录，最后将修改的文件复制到输出目录，这一步必须做不然编译会报错
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        if (outputProvider != null) {
            outputProvider.deleteAll();
        }

        final HelloExtension helloExtension = mProject.getExtensions().getByType(HelloExtension.class);
        mLogger.log(LogLevel.ERROR, "enableTimeCost = " + helloExtension.getEnableTimeCost());
        mLogger.log(LogLevel.ERROR, "maxTimeMonitor = " + helloExtension.getMaxTimeMonitor());
        // 遍历
        for (TransformInput input : inputs) {
            // 处理jar
            for (JarInput jarInput : input.getJarInputs()) {
                try {
                    transformJar(transformInvocation, jarInput);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 处理目录里面的Class
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                transformDirectory(transformInvocation, directoryInput, helloExtension);
            }
        }
    }

    private void transformJar(TransformInvocation invocation, JarInput input) throws Exception {
        File tempDir = invocation.getContext().getTemporaryDir();
        String destName = input.getFile().getName();
        String hexName = DigestUtils.md5Hex(input.getFile().getAbsolutePath()).substring(0, 8);
        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length() - 4);
        }
        // 获取输出路径
        File dest = invocation.getOutputProvider()
                .getContentLocation(destName + "_" + hexName, input.getContentTypes(), input.getScopes(), Format.JAR);

        JarFile originJar = new JarFile(input.getFile());
        File outputJar = new File(tempDir, "temp_" + input.getFile().getName());
        JarOutputStream output = new JarOutputStream(new FileOutputStream(outputJar));

        // 遍历原jar文件寻找class文件
        Enumeration<JarEntry> enumeration = originJar.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry originEntry = enumeration.nextElement();
            InputStream inputStream = originJar.getInputStream(originEntry);

            String entryName = originEntry.getName();
            if (entryName.endsWith(".class")) {
                JarEntry destEntry = new JarEntry(entryName);
                output.putNextEntry(destEntry);

                byte[] sourceBytes = IOUtils.toByteArray(inputStream);
                // 修改class文件内容
                byte[] modifiedBytes = referHackClass(sourceBytes);
                if (modifiedBytes == null) {
                    modifiedBytes = sourceBytes;
                }
                output.write(modifiedBytes);
                output.closeEntry();
            }
        }
        output.close();
        originJar.close();

        // 复制修改后jar到输出路径
        FileUtils.copyFile(outputJar, dest);
    }

    private void transformDirectory(TransformInvocation invocation, DirectoryInput input, HelloExtension helloExtension) throws IOException {
        /**************************************注册Transform后这个操作必须要做，否则会出现dex文件缺少项目中定义的相关class文件*************************************/
        File dest = invocation.getOutputProvider()
                .getContentLocation(input.getName(), input.getContentTypes(), input.getScopes(), Format.DIRECTORY);
        if (helloExtension.getEnableTimeCost()) {
            mLogger.log(LogLevel.ERROR, "transformDirectory if = " + helloExtension.getEnableTimeCost());
//            File tempDir = invocation.getContext().getTemporaryDir();
            // 获取输出路径
            File dir = input.getFile();
            if (dir != null && dir.exists()) {
                traverseDirectory(/*tempDir,*/ dir, helloExtension);
                FileUtils.copyDirectory(input.getFile(), dest);
            }
        } else {
            mLogger.log(LogLevel.ERROR, "transformDirectory else = " + helloExtension.getEnableTimeCost());
            FileUtils.copyDirectory(input.getFile(), dest);
        }
    }

    private void traverseDirectory(/*File tempDir,*/ File dir, HelloExtension helloExtension) throws IOException {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                traverseDirectory(/*tempDir,*/ file, helloExtension);
            } else if (file.getAbsolutePath().endsWith(".class")) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] sourceBytes = IOUtils.toByteArray(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);

                ClassReader classReader = new ClassReader(sourceBytes);
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                AutoClassVisitor cv = new AutoClassVisitor(Opcodes.ASM6, classWriter, helloExtension);

                // 开始执行
                classReader.accept(cv, EXPAND_FRAMES);

                if (cv.isNeedInject()) {
                    byte[] modifiedBytes = classWriter.toByteArray();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(modifiedBytes);
                    bufferedOutputStream.flush();
                    IOUtils.closeQuietly(bufferedOutputStream);
                    IOUtils.closeQuietly(fileOutputStream);
                    mLogger.log(LogLevel.ERROR, /*key +*/ "----" + file.getAbsolutePath());
                }
            }
        }
    }

    private byte[] referHackClass(byte[] inputStream) {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new AutoClassVisitor(Opcodes.ASM6, classWriter, null);

        classReader.accept(cv, EXPAND_FRAMES);
        return classWriter.toByteArray();
    }
}
