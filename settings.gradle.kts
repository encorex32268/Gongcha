pluginManagement {
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

rootProject.name = "Gongcha"
include(":app")
include(":auth:presentation")
include(":auth:data")
include(":auth:domain")
include(":menu:presentation")
include(":menu:data")
include(":menu:domain")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":cart:presentation")
include(":cart:data")
include(":cart:domain")
include(":history:presentation")
include(":history:data")
include(":history:domain")
include(":build-logic:convention")
