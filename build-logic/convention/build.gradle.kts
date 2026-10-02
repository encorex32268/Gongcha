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
    }

}