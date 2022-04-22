plugins {
    id("bundle-library")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    api(libs.tinylog.api)
}