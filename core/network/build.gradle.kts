plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.serialization) // @Serializable 사용을 위해 필수

    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.fortune.core.network"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 26
        targetSdk = 36

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
}

dependencies {
    implementation(project(":core:model"))

    // Retrofit & OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization) // Retrofit + Serialization 연결
    implementation(libs.okhttp.logging)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt 안드로이드 라이브러리
    implementation(libs.hilt.android)
    // 컴파일 타임에 어노테이션을 코드로 바꿔주는 컴파일러
    kapt(libs.hilt.compiler)
}