plugins {
    alias(libs.plugins.aungthiha.android.library)
}

android {
    namespace = "io.github.aungthiha.navigation.test"
}

dependencies {
    implementation(projects.navigation.core)

    implementation(libs.kotlinx.coroutines.test)

    implementation(platform(libs.junit.bom))
    implementation(libs.junit.jupiter)

    implementation(libs.koin.test)
}
