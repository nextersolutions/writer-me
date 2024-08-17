import io.writerme.app.Config
import io.writerme.app.Dependencies

plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = Config.Modules.domain
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = Config.testInstrumentationRunner
        buildConfigField("String", "STAGE", "\"${Config.stage}\"")

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //region modules
    implementation(project(Dependencies.Modules.core))
    implementation(project(Dependencies.Modules.data))
    implementation(project(Dependencies.Modules.resources))
    // end region

    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.activity)
    implementation(libs.androidx.core.splash)

    //region navigation
    implementation(libs.navigation.core)
    implementation(libs.navigation.compose)
    // end region

    //region coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test)
    // end region

    //region Lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewModel)
    implementation(libs.lifecycle.viewModelCompose)
    implementation(libs.lifecycle.viewModelSavedState)
    implementation(libs.lifecycle.compiler)
    //endregion

    //region DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.navigation.compose)
    //endregion

    implementation(libs.phonenumber.util)
    implementation(libs.parse.sdk)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
