import taskcontainers.createDependencySyncTask

plugins {
    java

    id("util")
}

// General information
group = "keincraft"
version = "0.1.0-SNAPSHOT"

val supportedOS = listOf("linux", "macos", "windows")

// Repositories for dependency downloads
repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

// Set git subprojects as source roots
sourceSets {
    named("main") {
        java {
            srcDir("keincraft")

            include("engine/**")
            include("main/**")
        }

        resources {
            srcDir("keincraft")

            include("**.properties")
            include("resources/**")
        }
    }

    named("test") {
        java {
            srcDir("keincraft-test")

            include("engine/**")
            include("main/**")
        }

        resources {
            srcDir("keincraft-test")

            include("**.properties")
            include("resources/**")
        }
    }
}

dependencies {
    // Compile Only
    compileOnly("org.checkerframework:checker-qual:3.21.1") // Checker annotations

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.1-SNAPSHOT")) // Lwjgl library versions

    // Libraries
    implementation("org.lwjgl", "lwjgl") // Core
    implementation("org.lwjgl", "lwjgl-glfw") // Window, Input
    implementation("org.lwjgl", "lwjgl-vulkan") // Graphics API

    implementation("org.tinylog:tinylog-impl:2.4.1") // Logging

    // Native libraries
    supportedOS.map { "natives-$it" }.forEach { native ->
        runtimeOnly("org.lwjgl", "lwjgl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = native)
    }

    runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = "natives-macos")

    // Test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2") // Test
    testImplementation("org.assertj:assertj-core:3.22.0") // Assertions
}

tasks {
    // Set java version
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }

    // Tests uses junit 5
    named<Test>("test") {
        useJUnitPlatform()
    }
}

tasks.createDependencySyncTask("syncDependencies", "keincraft/libs", provider {
    val runtimeClasspath = configurations.named("runtimeClasspath").get()
    val compileClasspath = configurations.named("compileClasspath").get()
    return@provider runtimeClasspath.plus(compileClasspath)
})
tasks.createDependencySyncTask("syncTestDependencies", "keincraft-test/libs", provider {
    val runtimeClasspath = configurations.named("runtimeClasspath").get()
    val compileClasspath = configurations.named("compileClasspath").get()
    val testRuntimeClasspath = configurations.named("testRuntimeClasspath").get()
    val testCompileClasspath = configurations.named("testRuntimeClasspath").get()
    return@provider testRuntimeClasspath.plus(testCompileClasspath).minus(runtimeClasspath).minus(compileClasspath)
})