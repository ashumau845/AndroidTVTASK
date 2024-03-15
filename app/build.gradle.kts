plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.mydemoapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mydemoapplication"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation("androidx.leanback:leanback:1.1.0-rc02")
    //Gson
    implementation("com.google.code.gson:gson:2.8.6")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")

    //Tab
    implementation ("androidx.leanback:leanback-tab:1.1.0-beta01")

    implementation ("androidx.constraintlayout:constraintlayout:1.1.3")

}