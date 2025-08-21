plugins {
    alias(libs.plugins.aungthiha.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.aungthiha.myfeature.presentation"
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.coil.network.okhttp)
    implementation(libs.coil)

    implementation(projects.myfeature.domain)

    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.composeViewModel)

    implementation(projects.navigation.core)
    implementation(projects.design)

    implementation(libs.androidx.core.ktx)
}
