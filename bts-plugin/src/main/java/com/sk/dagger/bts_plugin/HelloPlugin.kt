package com.sk.dagger.bts_plugin

import com.android.build.gradle.AppExtension
import com.sk.dagger.asm.TimeTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("hello plugin ${target.buildDir?.absolutePath}")
        // 添加一个扩展
        target.extensions.add("pkgInfo", HelloExtension::class.java)
        // 添加一个任务
        target.tasks.create("updatePkgInfo", HelloTask::class.java)
        initAsm(target)
    }

    private fun initAsm(target: Project) {
        val appExtension = target.extensions.getByType(AppExtension::class.java)
        appExtension.registerTransform(TimeTransform(target))
    }
}