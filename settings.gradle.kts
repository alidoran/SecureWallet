@file:Suppress("UnstableApiUsage")



rootProject.name = "SecureWallet"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal()
    }
}

include(":composeApp")
include(":core")
include(":core:basedomain")
include(":core:baseui")
include(":core:data")
include(":core:data:local")
include(":core:data:remote")
include(":core:data:repository")
include(":core:domain")
include(":di")
include(":features")
include(":features:auth")
include(":features:auth:domain")
include(":features:auth:ui")
include(":features:feature1")
include(":foundation:media")
