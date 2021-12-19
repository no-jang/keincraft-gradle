plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.guardsquare:proguard-gradle:7.2.0-beta4")
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.1")
}