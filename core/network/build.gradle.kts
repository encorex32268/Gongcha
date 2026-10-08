plugins {
    alias(libs.plugins.gongcha.android.library)
}
android {
    namespace = "com.lihan.core.network"
}

dependencies {
    implementation(projects.core.domain)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.bundles.koin)
    testImplementation(libs.junit)
}