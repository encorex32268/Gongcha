plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.menu.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(projects.menu.domain)
}