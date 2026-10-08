plugins {
    alias(libs.plugins.gongcha.android.library.compose)
}

android {
    namespace = "com.lihan.core.presentation.designsystem"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material3)

    androidTestDebugImplementation(libs.androidx.junit)
}