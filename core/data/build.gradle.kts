plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.network)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestDebugImplementation(libs.androidx.junit)
}