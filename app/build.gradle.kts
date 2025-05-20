plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.mockotisample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mockotisample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.mockotisample.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dataBinding {
        enable = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation("androidx.test:rules:1.6.1")


    // Coil - Image Loading
    implementation(libs.coil.kt.coil)
    //implementation("io.coil-kt:coil:2.4.0")
    //For decoding to svg
    implementation(libs.coil.kt.coil.svg)
    //implementation("io.coil-kt:coil-svg:2.2.2")
    //For compose
    implementation(libs.coil.kt.coil.compose)
    //implementation("io.coil-kt:coil-compose:2.4.0")

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)


    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.56.2")
    // ...with Kotlin.
    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.56.2")
    // ...with Java.
    //androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.56.2")

    //
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.2")

}