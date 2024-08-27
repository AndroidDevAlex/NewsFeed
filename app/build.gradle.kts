plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.newsfeed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsfeed"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.lifecycleRuntime.ktx)
    implementation(libs.lifecycleRuntime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.ui.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.test.compose.bom))
    androidTestImplementation(libs.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Navigation
    implementation(libs.nav.compose)
    implementation(libs.compose.material)

    //Coil
    implementation(libs.coil.compose)

    //Coroutine
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.coroutines)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Room
    ksp(libs.androidx.room)
    implementation(libs.room.common)
    implementation(libs.room.ktx)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp)

    //ViewModel
    implementation(libs.viewmodel.compose)
    implementation(libs.livedata.ktx)

    //Simple XML
    implementation(libs.converter.simplexml)

    //Jsoup
    implementation(libs.jsoup)

    //Swipe&refresh
    implementation(libs.swiperefresh)

    //Pagination
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.room.paging)
}