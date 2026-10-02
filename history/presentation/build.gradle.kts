plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.history.presentation"

}

dependencies {
    implementation(projects.history.domain)
}