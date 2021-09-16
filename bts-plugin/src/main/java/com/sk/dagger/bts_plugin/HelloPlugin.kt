package com.sk.dagger.bts_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("hello plugin ${target.buildDir?.absolutePath}")
    }
}