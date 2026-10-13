pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Gongcha"

include(":app")

include(":auth:presentation")
include(":menu:presentation")
include(":menu:data")
include(":menu:domain")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":cart:presentation")
include(":history:presentation")
include(":history:data")
include(":history:domain")
include(":core:network")
include(":member:presentation")

