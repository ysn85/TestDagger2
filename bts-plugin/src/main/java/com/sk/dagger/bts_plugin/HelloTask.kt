package com.sk.dagger.bts_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import groovy.util.XmlSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project
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
        val extensionValue = project.extensions.getByType(HelloExtension::class.java)
        println("hello task doMyTask! ${extensionValue?.versionInfo}")
        println("hello task doMyTask! ${extensionValue?.pkgName}")
        println("hello task doMyTask! ${extensionValue?.maxTimeMonitor}")
    }

    @TaskAction
    fun doMyTask2() {
        println("hello task doMyTask2")

        project.plugins.all {
            when (it) {
                is AppPlugin -> {
                    doPackName(
                        project,
                        project.extensions.getByType(AppExtension::class.java).applicationVariants
                    )
                }
                is LibraryPlugin -> {
                    doPackName(
                        project,
                        project.extensions.getByType(LibraryExtension::class.java).libraryVariants
                    )
                }
            }
        }
    }

    private fun doPackName(project: Project, variants: DomainObjectSet<out BaseVariant>) {
        variants.all { variant ->
            val xmlSlurper = XmlSlurper(false, false)
            val list = variant.sourceSets.map { it.manifestFile }
            val tempFile = list[0]
            val result = xmlSlurper.parse(list[0])
            val packName = result.getProperty("@package").toString()
            result.setProperty(
                "@package",
                project.extensions.getByType(HelloExtension::class.java)?.pkgName
            )
            println("doPackName packName is $packName")
        }
    }
}