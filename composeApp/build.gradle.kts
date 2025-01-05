import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
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
            implementation(project(":core:baseui"))
            implementation(project(":core:domain"))
        }
        commonMain.dependencies {
            implementation(libs.kodein.di.framework.compose)
            implementation(project(":core"))
            implementation(project(":core:baseui"))
            implementation(project(":core:domain"))
            implementation(project(":features:feature1"))
            implementation(project(":features:feature2"))
            implementation(project(":di"))
        }
        iosMain.dependencies {
            implementation(project(":core:baseui"))
            implementation(project(":core:domain"))
        }
        desktopMain.dependencies {
            implementation(project(":core:baseui"))
            implementation(project(":core:domain"))
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "ir.dorantech"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ir.dorantech"

        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "ir.dorantech.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ir.dorantech"
            packageVersion = "1.0.0"
        }
    }
}