plugins {
    alias(libs.plugins.gongcha.android.library)
}

android {
    namespace = "com.lihan.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.firebase.firestore)
}