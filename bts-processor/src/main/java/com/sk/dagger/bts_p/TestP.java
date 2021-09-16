package com.sk.dagger.bts_p;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.sk.dagger.bts_annotation.TestDe;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class TestP extends AbstractProcessor {
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "hello init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "hello Processor");
        for (Element e : roundEnvironment.getElementsAnnotatedWith(TestDe.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "hello Processor " + e.getSimpleName());
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of("com.sk.dagger.bts_annotation.TestDe");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        messager.printMessage(Diagnostic.Kind.NOTE, "hello getSupportedSourceVersion");
        return SourceVersion.latestSupported();
    }
}
