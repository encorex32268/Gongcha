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

    //TODO: Need to change this id , when git private to public.
    val webClientId = "148497736151-28j91ni5hsmi0o14omt0aiphb3ubh7vo.apps.googleusercontent.com"

    when(extensionType) {
        ExtensionType.APPLICATION -> {
            this@configureBuildTypes.extensions.configure<ApplicationExtension> {
                buildTypes {
                    debug {
                        configureDebugBuildType(webClientId = webClientId)
                    }
                    release {
                        configureReleaseBuildType(
                            webClientId = webClientId,
                            extensionType = extensionType,
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
                        configureDebugBuildType(webClientId = webClientId)
                    }
                    release {
                        configureReleaseBuildType(
                            webClientId = webClientId,
                            extensionType = extensionType,
                            defaultProguardFile = getDefaultProguardFile("proguard-android-optimize.txt")
                        )
                    }
                }
            }
        }
    }
}


private fun BuildType.configureDebugBuildType(webClientId: String){
    buildConfigField("String","WEB_CLIENT_ID","\"$webClientId\"")
}


private fun BuildType.configureReleaseBuildType(
    extensionType: ExtensionType,
    defaultProguardFile: File,
    webClientId: String
) {

    buildConfigField("String","WEB_CLIENT_ID","\"$webClientId\"")

    isMinifyEnabled = true
    if (extensionType == ExtensionType.APPLICATION) {
        isShrinkResources = true
    }
    proguardFiles(
        defaultProguardFile,
        "proguard-rules.pro"
    )
}