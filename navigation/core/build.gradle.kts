plugins {
    alias(libs.plugins.aungthiha.android.library)
}

android {
    namespace = "io.github.aungthiha.navigation.core"
}

dependencies {

    api(libs.androidx.navigation)

    implementation(libs.koin.core)

    testImplementation(libs.mockk)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
