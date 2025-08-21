plugins {
    alias(libs.plugins.aungthiha.kotlin.library)
}

dependencies {
    api(projects.operation.core)
    implementation(libs.koin.core)
}
