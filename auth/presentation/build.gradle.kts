plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.auth.presentation"
}

dependencies {
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.network)

    implementation(projects.auth.domain)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}