import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "wiki.rickandmorty"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName  = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationVariants.all {
            outputs.map { it as BaseVariantOutputImpl }.forEach {
                it.outputFileName =
                    "Rick And Morty Wiki $versionName ($versionCode)-${buildType.name}-${currentFormatDate}.apk"
            }
        }

    }

    signingConfigs {
        create("release") {
            keyAlias = "alias"
            keyPassword = "aliaspassword"
            storeFile = file("local.keystore")
            storePassword = "storepassword"
        }
    }

    buildTypes {
            release {
                isMinifyEnabled = true
                proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
                proguardFile(file("proguard-rules.pro"))
                signingConfig = signingConfigs.getByName("release")
            }

    }
    compileOptions {
        sourceCompatibility(1.11)
        targetCompatibility(1.11)
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1" // compose version
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation ("androidx.compose.material:material:1.1.1")
    implementation( "androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation( "androidx.activity:activity-compose:1.4.0")
    testImplementation( "junit:junit:4.13.2")
    androidTestImplementation( "androidx.test.ext:junit:1.1.3")
    androidTestImplementation( "androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation( "androidx.compose.ui:ui-test-junit4:1.1.1")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.1.1")

    //modo core
    implementation("com.github.terrakok:modo:0.6.3")
    //for navigation based on FragmentManager
    implementation("com.github.terrakok:modo-render-android-compose:0.6.3")
    implementation("io.coil-kt:coil-compose:2.0.0-rc02")
    implementation("io.insert-koin:koin-android:3.1.6")
   // implementation ("io.insert-koin:koin-android-ext:3.1.6")
    implementation ("io.insert-koin:koin-androidx-compose:3.1.6")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.6-alpha")
    implementation(project(":i-characters"))
    implementation(project(":cf-network"))
    implementation(project(":cf-data"))


}

val currentFormatDate: String
    get() {
        val date = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date.format(format)
    }
