package com.sk.dagger.bts_plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Task一定要加open修饰符否则，不能默认，默认为final修饰符
 */
open class HelloTask : DefaultTask() {

    init {
        group = "HelloTask"
        description = "this is a test task!"
    }

    @TaskAction
    fun doMyTask() {
        println("hello task doMyTask!")
    }
}