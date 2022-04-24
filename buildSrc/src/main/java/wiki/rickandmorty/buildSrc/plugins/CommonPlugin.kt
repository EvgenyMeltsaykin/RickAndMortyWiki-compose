package wiki.rickandmorty.buildSrc.plugins

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

class CommonPlugin : BasePlugin() {
    override fun apply(target: Project) = target.applyAndroid()

    private fun Project.applyAndroid() {
        plugins.run {
            apply("com.android.library")
            apply("kotlin-parcelize")
            apply("org.jetbrains.kotlin.android")
        }

        android {
            compileSdkVersion(31)
        }
    }
}
