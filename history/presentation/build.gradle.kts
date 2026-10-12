plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.history.presentation"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.history.domain)

    implementation(libs.koin.androidx.compose)
}