rootProject.name = "MvvmKmpLargeScale"
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
    }
}

include(":composeApp")
include(":core")
include(":core:data")
include(":core:data:local")
include(":core:data:remote")
include(":core:data:repository")
include(":core:data:mapper")
include(":core:domain")
include(":core:baseui")
include(":di")
include(":foundation")
include(":features")
include(":features:feature1")
include(":features:feature2")
