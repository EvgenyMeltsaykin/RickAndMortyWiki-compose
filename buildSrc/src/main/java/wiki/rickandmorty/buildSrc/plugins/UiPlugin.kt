package wiki.rickandmorty.buildSrc.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class UiPlugin: BasePlugin() {
    override fun apply(target: Project) = target.applyAndroid()

    private fun Project.applyAndroid() {
        plugins.run {
            apply("com.android.library")
            apply("common-plugin")
        }

        android {
            buildFeatures.compose = true

            composeOptions {
                kotlinCompilerExtensionVersion = "1.1.1" // compose version
            }

            dependencies{
                implementation("androidx.compose.ui:ui:1.1.1")
                implementation ("androidx.compose.material:material:1.1.1")
                implementation( "androidx.compose.ui:ui-tooling-preview:1.1.1")
                implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
                implementation( "androidx.activity:activity-compose:1.4.0")

                implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
                implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.6-alpha")

                implementation("io.coil-kt:coil-gif:2.0.0-rc02")
                implementation("io.coil-kt:coil-compose:2.0.0-rc02")
            }
        }

    }

}