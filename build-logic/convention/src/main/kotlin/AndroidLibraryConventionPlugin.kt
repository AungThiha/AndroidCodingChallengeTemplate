import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            val libs =
                extensions.getByType(org.gradle.api.artifacts.VersionCatalogsExtension::class.java)
                    .named("libs")

            extensions.configure<LibraryExtension> {
                compileSdk = libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()

                defaultConfig {
                    minSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
                }
            }

            kotlinExtension.jvmToolchain(
                libs.findVersion("jvmToolchain").get().requiredVersion.toInt()
            )
        }
    }
}
