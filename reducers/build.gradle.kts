plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
}

android {
    namespace = "dev.smartification.reducers"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.hilt)
    testImplementation(libs.junit)
}