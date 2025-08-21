plugins {
    alias(libs.plugins.aungthiha.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.aungthiha.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.koin.core)
    
    api(projects.operation.core)

    api(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    api(libs.retrofit.core)
    api(libs.retrofit.kotlin.serialization)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
