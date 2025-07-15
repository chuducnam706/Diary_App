plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.diaryapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.diaryapp"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.7.1")
    implementation(libs.viewpager2)
    annotationProcessor("androidx.room:room-compiler:2.7.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.tbuonomo:dotsindicator:5.1.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}