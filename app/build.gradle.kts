plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val obfuscationEnabled = getBooleanProperty("obfuscationEnabled", false)

android {
    compileSdk = Config.compileSdk
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdkPreview = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildType.release) {
            booleanBuildConfigField("IS_DEBUG", false)

            stringBuildConfigField("MAIN_API_BASE_URL_DEV", "https://api.minerstat.com/")
            stringBuildConfigField("REFRESH_TOKEN_URL_DEV", "http://176.99.12.176/")

            isMinifyEnabled = obfuscationEnabled
            isShrinkResources = obfuscationEnabled
        }
        getByName(BuildType.debug) {
            booleanBuildConfigField("IS_DEBUG", true)

            stringBuildConfigField("MAIN_API_BASE_URL_DEV", "https://api.minerstat.com/")
            stringBuildConfigField("REFRESH_TOKEN_URL_DEV", "http://176.99.12.176/")
            isDebuggable = true
            isMinifyEnabled = obfuscationEnabled
            isShrinkResources = obfuscationEnabled
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    enableViewBinding()
}

dependencies {
    implementation(Deps.kotlin)

    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.di))
    implementation(project(Modules.Common.presentation))
    implementation(project(Modules.Common.mvi))
    implementation(project(Modules.Common.navigation))
    implementation(project(Modules.Common.util))
    implementation(project(Modules.Common.uikit))
//    implementation(project(Modules.Common.component))
    implementation(project(Modules.Common.network))

    implementation(project(Modules.Feature.main.api))
    implementation(project(Modules.Feature.main.impl))

    implementation(project(Modules.Feature.notes.api))
    implementation(project(Modules.Feature.notes.impl))

    implementation(project(Modules.Feature.settings.api))
    implementation(project(Modules.Feature.settings.impl))

    implementation(project(Modules.Feature.knowledge.api))
    implementation(project(Modules.Feature.knowledge.impl))

    implementation(project(Modules.Feature.mining.api))
    implementation(project(Modules.Feature.mining.impl))

    implementation(project(Modules.Feature.exchangeRate.api))
    implementation(project(Modules.Feature.exchangeRate.impl))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.fragment)
    implementation(Deps.AndroidX.coordinatorLayout)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.paging)
    implementation(Deps.AndroidX.navigationFragment)
    implementation(Deps.AndroidX.navigationUi)
    implementation(Deps.AndroidX.swipeRefreshLayout)
    implementation(Deps.AndroidX.legacy)

    implementation(Deps.Android.material)

    implementation(Deps.adapterDelegates)

    implementation(Deps.Koin.core)

    implementation(Deps.workRuntime)

    androidTestImplementation(Deps.Tests.androidxJunit)
    androidTestImplementation(Deps.Tests.espresso)
    androidTestImplementation(Deps.Tests.junit)

    coreLibraryDesugaring(Deps.Tools.desugar)
}
