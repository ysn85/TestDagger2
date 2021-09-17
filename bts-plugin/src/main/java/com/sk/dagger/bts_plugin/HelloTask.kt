package com.sk.dagger.bts_plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

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