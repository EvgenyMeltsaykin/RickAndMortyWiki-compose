plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-plugin") {
            id = "common-plugin"
            implementationClass = "wiki.rickandmorty.buildSrc.plugins.CommonPlugin"
        }
        register("feature-plugin") {
            id = "feature-plugin"
            implementationClass = "wiki.rickandmorty.buildSrc.plugins.FeaturePlugin"
        }

        register("ui-plugin") {
            id = "ui-plugin"
            implementationClass = "wiki.rickandmorty.buildSrc.plugins.UiPlugin"
        }

        register("network-plugin") {
            id = "network-plugin"
            implementationClass = "wiki.rickandmorty.buildSrc.plugins.NetworkPlugin"
        }

    }
}
repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.android.tools.build:gradle:7.1.3")
}
