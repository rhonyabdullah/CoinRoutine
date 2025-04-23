rootProject.name = "CoinRoutine"
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
        // Kotzilla
        maven {
            name = "kotzilla"
            url = uri("https://repository.kotzilla.io/repository/kotzilla-platform/")
        }
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
        // Kotzilla
        maven {
            name = "kotzilla"
            url = uri("https://repository.kotzilla.io/repository/kotzilla-platform/")
        }
    }
}

include(":composeApp")