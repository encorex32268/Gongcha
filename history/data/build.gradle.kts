plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.history.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.network)

    implementation(projects.history.domain)

    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.coroutines.core)
    androidTestDebugImplementation(libs.androidx.junit)
}