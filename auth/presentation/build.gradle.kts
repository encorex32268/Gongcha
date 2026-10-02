plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
}