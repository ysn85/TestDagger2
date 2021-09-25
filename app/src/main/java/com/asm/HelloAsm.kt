//package com.asm
//
//import com.annotation.StudentJ
//import org.objectweb.asm.ClassReader
//import org.objectweb.asm.ClassReader.EXPAND_FRAMES
//import org.objectweb.asm.Opcodes
//import org.objectweb.asm.tree.ClassNode
//import java.io.File
//import java.io.FileInputStream
//
//class HelloAsm {
//
//    fun testAsm() {
//        val clazz = StudentJ::class.java
//        val filePath = getClazzFilePath(clazz)
//        val clazzReader = ClassReader(FileInputStream(filePath))
//        val clazzNode = ClassNode(Opcodes.ASM6)
//        clazzReader.accept(clazzNode, EXPAND_FRAMES)
//
//        val clazzMethods = clazzNode.methods
//        val clazzFields = clazzNode.fields
//
//        repeat(clazzMethods.size) {
//
//        }
//    }
//
//    private fun getClazzFilePath(clazz: Class<*>): String {
//        val buildDir = clazz.protectionDomain?.codeSource?.location?.file
//        val fileName = clazz.simpleName + ".class"
//        val file = File(
//            buildDir + clazz.getPackage().name.replace("[.]", "/").toString() + "/",
//            fileName
//        )
//        return file.absolutePath
//    }
//}