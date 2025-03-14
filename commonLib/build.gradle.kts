plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.zhq.commonlib"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
        buildConfig = true
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

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity)
   //Compose
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    //material
    api(libs.material)
    api(libs.androidx.material)
    api(libs.androidx.material3)
    //paging
    api(libs.androidx.paging.runtime)
    api(libs.androidx.paging.compose)
    //Retrofit
    api(libs.retrofit)
    api(libs.converterGson)
    api(libs.persistentCookieJar)
    api(libs.logging.interceptor)
    //图片
    api(libs.coil)
    api(libs.androidx.runtime.livedata)
    //Navigation Compose
    api(libs.androidx.navigation.compose)
//    api(libs.kotlinx.serialization.json)
    //MMKV
    api(libs.mmkv)

    testApi(libs.junit)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}