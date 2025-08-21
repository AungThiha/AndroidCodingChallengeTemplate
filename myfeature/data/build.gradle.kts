plugins {
    alias(libs.plugins.aungthiha.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.aungthiha.myfeature.data"
}

dependencies {
    implementation(projects.myfeature.domain)
    implementation(projects.network)
    implementation(libs.koin.core)
    implementation(libs.androidx.core.ktx)
}
