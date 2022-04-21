import org.gradle.api.internal.FeaturePreviews

// FIXME: Gradle does not generate dependency accessors from this file. Gradle version 7.6 might fix it.
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

// Activates all incubating features
FeaturePreviews.Feature.values().forEach { feature ->
    if (feature.isActive) {
        enableFeaturePreview(feature.name)
    }
}