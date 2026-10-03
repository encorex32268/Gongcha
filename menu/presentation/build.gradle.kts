plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.menu.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.presentation.ui)
    implementation(projects.menu.domain)

    implementation(libs.coil.compose)
}