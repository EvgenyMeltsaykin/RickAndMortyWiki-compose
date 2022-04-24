plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android{
    compileSdk = 31
    defaultConfig{
        minSdk = 24
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation(project(":cf-data"))
    testImplementation("junit:junit:4.12")
}