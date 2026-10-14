plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.member.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.presentation.ui)

    implementation(libs.coil.compose)
    implementation(libs.qrcode.core)
}