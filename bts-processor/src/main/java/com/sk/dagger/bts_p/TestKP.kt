package com.sk.dagger.bts_p

import com.google.auto.service.AutoService
import java.util.LinkedHashSet
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class TestKP : AbstractProcessor() {

    private var message: Messager? = null

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        message = p0?.messager
        printProcessorMsg("hello kt init")
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        printProcessorMsg("hello kt process")
//        val set = p1.getElementsAnnotatedWith(TestKDe::class)
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        printProcessorMsg("hello kt getSupportedSourceVersion")
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        printProcessorMsg("hello kt getSupportedAnnotationTypes")
        val set = LinkedHashSet<String>()
        set.add("com.sk.dagger.bts_annotation.TestKDe")
        return set
    }

    private fun printProcessorMsg(msg: String) {
        message?.printMessage(Diagnostic.Kind.NOTE, msg)
    }
}
