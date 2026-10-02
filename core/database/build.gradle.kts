plugins {
    alias(libs.plugins.gongcha.android.library)
    alias(libs.plugins.gongcha.android.room)
}

android {
    namespace = "com.lihan.core.database"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
}