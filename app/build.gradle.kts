plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
}

android {
    namespace = Application.appId
    compileSdk = Application.compileSdk

    defaultConfig {
        applicationId = Application.appId
        minSdk = Application.minSdk
        multiDexEnabled = true
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.compose_compiler_version
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(platform(Kotlin.kotlin_bom))
    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.livedateLifcycleRuntime)
    implementation(AndroidX.appCompatActivity)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.fragmentKtx)
    testImplementation(Junit.junit)
    androidTestImplementation(Junit.junitTestExt)
    androidTestImplementation(Junit.junitTestExtKtx)
    androidTestImplementation(Espresso.espresso)


    //COMPOSE
    implementation(platform(Compose.composeBoom))
    androidTestImplementation(Compose.composeBoom)

    implementation(Compose.compose_activity)
    implementation(Compose.compose_material_3)
    implementation(Compose.compose_preview)
    implementation(Compose.compose_ui_tooling)
    implementation(Compose.compose_ui_graphics)
    implementation(Compose.compose_compiler)
    implementation(Compose.compose_constraint_layout)
    implementation(Compose.compose_viewModel)
    implementation(Compose.compose_view_binding)
    debugImplementation(Compose.compose_ui_manifest)
    debugImplementation(Compose.compose_ui_tooling)
}