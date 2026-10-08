plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.auth.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(projects.auth.domain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)

    androidTestDebugImplementation(libs.androidx.junit)
}