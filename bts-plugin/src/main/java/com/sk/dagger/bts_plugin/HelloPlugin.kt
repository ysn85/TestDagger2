package com.sk.dagger.bts_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("hello plugin ${target.buildDir?.absolutePath}")
        // 添加一个扩展
        target.extensions.add("pkgInfo", HelloExtension::class.java)
        // 添加一个任务
        target.tasks.create("updatePkgInfo", HelloTask::class.java)
    }
}