plugins {
    java

    id("config-build")
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

sourceSets {
    main {
        java {
            srcDir("keincraft")

            include("engine/**")
        }

        resources {
            srcDir("keincraft")

            include("**.properties")
            include("resources/**")
        }
    }

    test {
        java {
            srcDir("keincraft-test")

            include("engine/**")
        }

        resources {
            srcDir("keincraft-test")

            exclude("engine/**")
        }
    }
}

val nativeOS = arrayOf("natives-linux", "natives-macos", "natives-windows")

dependencies {
    compileOnly("org.checkerframework:checker-qual:3.21.0") // Checker annotations

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.1-SNAPSHOT")) // Lwjgl Library Versions

    implementation("org.lwjgl", "lwjgl") // Core
    implementation("org.lwjgl", "lwjgl-glfw") // Window, Input
    implementation("org.lwjgl", "lwjgl-opengl") // Graphics API
    implementation("org.lwjgl", "lwjgl-stb") // Image Loading
    implementation("org.lwjgl", "lwjgl-vulkan") // Graphics API
    implementation("org.lwjgl", "lwjgl-assimp") // Model Loading
    implementation("org.lwjgl", "lwjgl-openal") // Audio
    implementation("org.lwjgl", "lwjgl-vma") // Vulkan Memory Allocator
    implementation("org.lwjgl", "lwjgl-shaderc") // Shader Compiler

    implementation("io.github.spair", "imgui-java-lwjgl3", "1.84.1.3") // GUI

    implementation("org.joml:joml:1.10.2") // Math
    implementation("org.tinylog:tinylog-impl:2.4.1") // Logging

    // Native Libraries
    nativeOS.forEach { native ->
        runtimeOnly("org.lwjgl", "lwjgl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = native)

        runtimeOnly("io.github.spair", "imgui-java-$native", "1.84.1.3")
    }

    // Only needed for macos
    runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = "natives-macos")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2") // Test
    testImplementation("com.google.truth:truth:1.1.3") // Assertions
}

tasks {
    named<Test>("test") {
        useJUnitPlatform()
    }

    create<Sync>("copyLibraries") {
        from(configurations.runtimeClasspath)
        into("keincraft/libs")
        preserve {
            include("*.txt")
        }
    }
}