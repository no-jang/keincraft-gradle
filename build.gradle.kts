plugins {
    java
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
    testImplementation("com.google.truth:truth:1.1.3") // Assertions
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

    // Copy libraries in keincraft subproject libs directory
    create<Sync>("copyLibraries") {
        from(configurations.named("runtimeClasspath"))
        into("keincraft/libs")
        preserve {
            include("*.txt")
        }
    }

    // Copy libraries in keincraft subproject libs directory
    create<Sync>("copyTestLibraries") {
        from(provider {
            configurations.named("testRuntimeClasspath").get().minus(configurations.named("runtimeClasspath").get())
        })
        into("keincraft-test/libs")
        preserve {
            include("*.txt")
        }
    }
}