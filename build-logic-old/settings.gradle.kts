import org.gradle.api.internal.FeaturePreviews.Feature

// FIXME: Gradle does not generate dependency accessors from this file. Gradle version 7.6 might fix it.
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("common")

include("basic")
include("engine")
include("java")
include("keincraft")

// Activates all incubating features
Feature.values().forEach { feature ->
    if (feature.isActive) {
        enableFeaturePreview(feature.name)
    }
}