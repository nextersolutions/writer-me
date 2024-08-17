import io.writerme.app.Config
import io.writerme.app.Dependencies

plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = Config.Modules.resources
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = Config.testInstrumentationRunner

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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    //region modules
    implementation(project(Dependencies.Modules.core))
    // end region

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)

    //region compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.livedata)
    implementation(libs.compose.runtime)
    implementation(libs.accompanist.controller)
    implementation(libs.accompanist.permissions)
    implementation(libs.coil)
    // end region

    //region Navigation
    implementation(libs.navigation.core)
    implementation(libs.navigation.compose)
    // end region

    //region coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    // end region

    //region play services
    implementation(libs.play.services.auth)
    // end region

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
