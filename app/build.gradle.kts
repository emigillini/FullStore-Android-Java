plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fullstore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fullstore"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation (libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation (libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation (libs.stripe)
    implementation(libs.glide)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation (libs.gridlayout)
    implementation (libs.cardview)
    annotationProcessor(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.mockito:mockito-core:4.6.1")
    testImplementation ("org.mockito:mockito-inline:4.6.1")


}