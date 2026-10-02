package com

import com.android.build.api.dsl.LibraryExtension
import com.lihan.convention.ExtensionType
import com.lihan.convention.configureAndroidCompose
import com.lihan.convention.configureBuildTypes
import com.lihan.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            with(pluginManager) {
                apply("gongcha.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}