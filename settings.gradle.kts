rootProject.name = "keincraft"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "proguard") {
                useModule("com.guardsquare:proguard-gradle:7.2.0-beta6")
            }
        }
    }
}