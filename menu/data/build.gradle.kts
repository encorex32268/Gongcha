plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.menu.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.menu.domain)

    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.coroutines.core)
}