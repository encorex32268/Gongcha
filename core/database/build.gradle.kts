plugins {
    alias(libs.plugins.gongcha.android.library)
    alias(libs.plugins.gongcha.android.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.lihan.core.database"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.core.domain)

    testImplementation(libs.junit)
}