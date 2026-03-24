plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.fortune.feature.login"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core modules (프로젝트 구조에 맞게 조정)
    implementation(project(":core:domain"))

    // Fragment KTX - viewModels() 델리게이트 사용을 위해 필수
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    // Navigation - findNavController() 사용을 위해 필수
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    // Compose - ComposeView 사용을 위해 필수
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Hilt 라이브러리
    implementation(libs.hilt.android)
    implementation(libs.mediation.test.suite)
    kapt(libs.hilt.compiler)

    // ViewModel과 Navigation 연결을 위해 필수 (이게 있어야 hiltViewModel() 사용 가능)
    implementation(libs.androidx.hilt.navigation.compose)
    // ViewModel 코루틴 확장을 위해 필수! (viewModelScope)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Compose에서 뷰모델 상태 관리를 위해 (collectAsState 등)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}