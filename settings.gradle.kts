rootProject.name = "keincraft"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle-plugins")
}

include("engine")
include("engine:common")