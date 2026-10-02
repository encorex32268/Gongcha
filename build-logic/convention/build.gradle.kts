plugins {
    `kotlin-dsl`
}

group = "com.lihan.gongcha.buildlogic"


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}


gradlePlugin {
    plugins {
        register("androidApplication"){
            id = "gongcha.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "gongcha.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary"){
            id = "gongcha.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "gongcha.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "gongcha.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

        register("androidRoom") {
            id = "gongcha.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("jvmLibrary") {
            id = "gongcha.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("jvmKtor") {
            id = "gongcha.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }

    }

}