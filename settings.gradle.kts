rootProject.name = "keincraft"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle-plugins")
}

include("engine")
include("engine:common")
include("engine:core")
include("engine:graphics")
include("engine:graphics-opengl")
include("engine:graphics-vulkan")