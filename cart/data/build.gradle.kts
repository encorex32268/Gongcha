plugins {
    alias(libs.plugins.gongcha.android.library)
}


android {
    namespace = "com.lihan.cart.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(projects.cart.domain)
}