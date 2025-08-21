plugins {
    alias(libs.plugins.aungthiha.kotlin.library)
}

dependencies {
    implementation(projects.coroutines.core)
    api(libs.kotlinx.coroutines.test)

    implementation(platform(libs.junit.bom))
    implementation(libs.junit.jupiter)
}
