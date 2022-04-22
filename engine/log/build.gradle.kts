plugins {
    id("bundle-library")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    api(projects.engine.log.api)

    implementation(libs.tinylog.impl)
    implementation(libs.tinylog.modern)
}