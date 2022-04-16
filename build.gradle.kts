buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven(url = "https://dl.bintray.com/ekito/koin")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
        classpath ("com.google.gms:google-services:4.3.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
