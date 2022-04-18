import org.gradle.api.internal.FeaturePreviews

rootProject.name = "keincraft-gradle"

pluginManagement  {
    includeBuild("gradle-plugins")
}

// All subprojects - alphabetical order
include("engine")
include("engine:log")
include("engine:log:tinylog")

// Activates all incubating features
FeaturePreviews.Feature.values().forEach { feature ->
    if (feature.isActive) {
        enableFeaturePreview(feature.name)
    }
}