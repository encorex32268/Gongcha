plugins {
    alias(libs.plugins.gongcha.android.feature.ui)
}

android {
    namespace = "com.lihan.cart.presentation"
}

dependencies {
    implementation(projects.cart.domain)
}