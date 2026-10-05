plugins {
    alias(libs.plugins.gongcha.android.library.compose)
}

android {
    namespace = "com.lihan.core.presentation.ui"
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.extended)
    androidTestDebugImplementation(libs.androidx.junit)

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
}