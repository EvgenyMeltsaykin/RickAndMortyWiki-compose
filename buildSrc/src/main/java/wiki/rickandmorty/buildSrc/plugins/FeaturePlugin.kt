package wiki.rickandmorty.buildSrc.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeaturePlugin: BasePlugin() {
    override fun apply(target: Project) = target.applyAndroid()

    private fun Project.applyAndroid() {
        plugins.run {
            apply("com.android.library")
            apply("common-plugin")
            apply("ui-plugin")
        }

        android {
            buildFeatures.compose = true

            composeOptions {
                kotlinCompilerExtensionVersion = "1.1.1" // compose version
            }

            dependencies{
                implementation("io.insert-koin:koin-android:3.1.6")
                implementation ("io.insert-koin:koin-androidx-compose:3.1.6")

                //modo core
                implementation("com.github.terrakok:modo:0.6.3")
                //for navigation based on FragmentManager
                implementation("com.github.terrakok:modo-render-android-compose:0.6.3")

                implementation(project(":cf-core"))
                implementation(project(":cf-data"))
                implementation(project(":cf-ui"))

            }
        }



    }


}