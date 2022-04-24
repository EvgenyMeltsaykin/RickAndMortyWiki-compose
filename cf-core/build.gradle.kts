plugins {
    id("ui-plugin")
}

dependencies {
    implementation("com.github.terrakok:modo:0.6.3")
    //for navigation based on FragmentManager
    implementation("com.github.terrakok:modo-render-android-compose:0.6.3")

    implementation("io.insert-koin:koin-android:3.1.6")
    implementation ("io.insert-koin:koin-androidx-compose:3.1.6")
    implementation(project(":cf-network"))
}