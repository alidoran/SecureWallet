plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "ir.dorantech.local"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    androidTarget {
        jvmToolchain(11)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        androidMain.dependencies {
            implementation(project(":core"))
            implementation(libs.sqldelight.android)
        }
        commonMain.dependencies {
            implementation(project(":core"))
            api(libs.sqldelight.runtime)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native)
        }
        desktopMain.dependencies {
            implementation(libs.sqldelight.driver)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("ir.dorantech")
        }
    }
}