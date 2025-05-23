import java.util.Base64

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
    kotlin("plugin.serialization") version "2.0.21"
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.elevii.comidanamedida"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.elevii.comidanamedida"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = project.findProperty("API_KEY") as String? ?: System.getenv("API_KEY") ?: ""
        val apiAuth = project.findProperty("API_AUTHORIZATION") as String? ?: System.getenv("API_AUTHORIZATION") ?: ""

        buildConfigField("String", "API_KEY", "\"$apiKey\"")

        buildConfigField("String", "API_AUTHORIZATION", "\"$apiAuth\"")
    }

    signingConfigs {
        create("release") {
            val keystoreBase64Env = System.getenv("KEYSTORE_BASE64") ?: ""
            val keystorePasswordEnv = System.getenv("KEYSTORE_PASSWORD") ?: ""
            val keyAliasEnv = System.getenv("KEY_ALIAS") ?: ""
            val keyPasswordEnv = System.getenv("KEY_PASSWORD") ?: ""

            val keystoreFile = layout.buildDirectory.file("keystore.jks").get().asFile

            if (!keystoreFile.exists() && keystoreBase64Env.isNotEmpty()) {
                val decoded = Base64.getDecoder().decode(keystoreBase64Env)
                keystoreFile.writeBytes(decoded)
            }

            storeFile = keystoreFile
            storePassword = keystorePasswordEnv
            keyAlias = keyAliasEnv
            keyPassword = keyPasswordEnv
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.room)
    kapt(libs.room.annotation)
    // kapt(libs.room.ktx)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.ksp)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.support)
    implementation(libs.navigation.testing)
    implementation(libs.kotlinx.serialization)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter)
    implementation(libs.squareup.okhttp3)
    implementation(libs.androidx.splashscreen)
}

subprojects {
    plugins.withId("io.gitlab.arturbosch.detekt") {
        extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            config.setFrom(file("${rootProject.rootDir}/config/detekt/detekt.yml"))
        }
    }
}
