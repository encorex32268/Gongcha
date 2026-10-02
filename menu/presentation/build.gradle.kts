plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.menu.presentation"
}

dependencies {
    implementation(projects.menu.domain)
}