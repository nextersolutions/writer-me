import io.writerme.app.Config
import io.writerme.app.Dependencies
import org.jetbrains.kotlin.konan.properties.Properties


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = Config.applicationId
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = Config.versionName
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    signingConfigs {
        create("production") {
            val properties =
                Properties().apply {
                    load(File(rootDir, "app/keys/keystore.properties").reader())
                }
            storeFile = file(properties.getProperty("STORE_FILE").toString())
            storePassword = properties.getProperty("STORE_PASSWORD").toString()
            keyAlias = properties.getProperty("KEY_ALIAS").toString()
            keyPassword = properties.getProperty("KEY_PASSWORD").toString()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".debug"
        }
    }

    flavorDimensions.add("distribution")

    productFlavors {
        create("production") {
            signingConfig = signingConfigs.getByName("production")
            dimension = "distribution"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_round"
            manifestPlaceholders["appName"] = "@string/app_name"
            // val parseApplicationId = getLocalPropertyKey(GradleKey.PARSE_APPLICATION_ID)
            // buildConfigField("String", GradleKey.PARSE_APPLICATION_ID, "\"$parseApplicationId\"")
        }
        create("staging") {
            signingConfig = signingConfigs.getByName("production")
            dimension = "distribution"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_round"
            manifestPlaceholders["appName"] = "@string/app_name_staging"
            // val parseApplicationId = getLocalPropertyKey(GradleKey.PARSE_APPLICATION_ID)
            // buildConfigField("String", GradleKey.PARSE_APPLICATION_ID, "\"$parseApplicationId\"")
        }
        create("development") {
            dimension = "distribution"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_round"
            manifestPlaceholders["appName"] = "@string/app_name_debug"
            // val parseApplicationId = getLocalPropertyKey(GradleKey.PARSE_APPLICATION_ID)
            // buildConfigField("String", GradleKey.PARSE_APPLICATION_ID, "\"$parseApplicationId\"")
        }
    }

    applicationVariants.all {
        outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "writerme-${baseName}_v$versionName.apk"
                output.outputFileName = outputFileName
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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //region modules
    implementation(project(Dependencies.Modules.core))
    implementation(project(Dependencies.Modules.resources))
    implementation(project(Dependencies.Modules.network))
    implementation(project(Dependencies.Modules.data))
    implementation(project(Dependencies.Modules.domain))
    implementation(project(Dependencies.Modules.Features.onboarding))
    // end region

    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.activity)
    implementation(libs.androidx.core.splash)

    //region Settings
    implementation(libs.androidx.multidex)
    //endregion

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
    implementation(libs.compose.livedata)
    implementation(libs.compose.runtime)
    implementation(libs.accompanist.controller)
    implementation(libs.accompanist.permissions)
    implementation(libs.coil)
    // end region

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

    //region test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    // end region
}

fun getLocalPropertyKey(key: String): String {
    val localProperties =
        com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir, providers)
    return if (localProperties.containsKey(key)) {
        localProperties.getProperty(key)
    } else {
        System.getenv(key)
            ?: error("$key not found in local properties or environment variables")
    }
}
