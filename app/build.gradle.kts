plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.uwazitek"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.uwazitek"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.ui) // Check for the latest version
    implementation (libs.androidx.material) // Material Design components
    implementation (libs.ui.tooling.preview) // Preview support
    implementation (libs.androidx.lifecycle.runtime.ktx.v262)
    implementation (libs.androidx.activity.compose.v161)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.compose.v260)
    implementation(libs.androidx.navigation.compose)
    implementation (libs.material3)
    implementation (libs.androidx.material.v100)
    implementation (libs.androidx.material.v150)
    implementation (libs.material3.v100)
    implementation (libs.androidx.material)

    implementation (libs.androidx.ui.v140)
    implementation (libs.androidx.material.v140)
    implementation (libs.androidx.ui.tooling.preview.v140)

    implementation (libs.androidx.material.icons.extended)
    implementation (libs.coil.kt.coil.compose)
    implementation (libs.coil.kt.coil.compose)

    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
}
