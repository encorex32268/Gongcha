plugins {
    alias(libs.plugins.gongcha.android.application.compose)
    //push
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.lihan.gongcha"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.bundles.koin.compose)

    implementation(libs.androidx.navigation)

    //push用
    implementation(libs.firebase.firestore)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.presentation.ui)

    implementation(projects.auth.data)
    implementation(projects.auth.domain)
    implementation(projects.auth.presentation)

    implementation(projects.cart.data)
    implementation(projects.cart.domain)
    implementation(projects.cart.presentation)

    implementation(projects.menu.data)
    implementation(projects.menu.domain)
    implementation(projects.menu.presentation)

    implementation(projects.history.data)
    implementation(projects.history.domain)
    implementation(projects.history.presentation)

    testImplementation(libs.junit)
}