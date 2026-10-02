package com.lihan.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension,
    extensionType: ExtensionType
) {
    commonExtension.buildFeatures.apply { buildConfig = true }

//    val apiKey = gradleLocalProperties(rootDir, rootProject.providers).getProperty("API_KEY")

    when(extensionType) {
        ExtensionType.APPLICATION -> {
            this@configureBuildTypes.extensions.configure<ApplicationExtension> {
                buildTypes {
                    debug {

                    }
                    release {
                        configureReleaseBuildType(
                            defaultProguardFile = getDefaultProguardFile("proguard-android-optimize.txt")
                        )
                    }
                }
            }
        }
        ExtensionType.LIBRARY -> {
            this@configureBuildTypes.extensions.configure<LibraryExtension> {
                buildTypes {
                    debug {

                    }
                    release {
                        configureReleaseBuildType(
                            defaultProguardFile = getDefaultProguardFile("proguard-android-optimize.txt")
                        )
                    }
                }
            }
        }
    }
}


private fun BuildType.configureReleaseBuildType(
    defaultProguardFile: File
) {
    isShrinkResources =true
    isMinifyEnabled = true
    proguardFiles(
        defaultProguardFile,
        "proguard-rules.pro"
    )
}