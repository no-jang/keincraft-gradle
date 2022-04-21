import org.gradle.api.internal.FeaturePreviews

rootProject.name = "keincraft-gradle"

pluginManagement  {
    includeBuild("build-logic")
}

// All subprojects - alphabetical order
include("engine")
include("engine:log")

// Activates all incubating features
FeaturePreviews.Feature.values().forEach { feature ->
    if (feature.isActive) {
        enableFeaturePreview(feature.name)
    }
}