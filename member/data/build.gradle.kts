plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.member.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(projects.member.domain)
}