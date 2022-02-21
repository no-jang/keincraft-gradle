//import task.registerUploadCoverageTask

plugins {
    java
    jacoco

    id("coverage.coverage")
    id("sync.sync-deps")
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

afterEvaluate {
    sourceSets.forEach {
        println(it.name)
        println(it.compileClasspath.files)
    }
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

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.1")) // Lwjgl library versions

    // Libraries
    implementation("org.lwjgl", "lwjgl") // Core
    implementation("org.lwjgl", "lwjgl-glfw") // Window, Input
    implementation("org.lwjgl", "lwjgl-vulkan") // Graphics API


    implementation("org.lwjgl", "lwjgl-shaderc")

//    implementation("org.lwjgl3:demo:3.3.1")
    implementation("org.joml:joml:1.10.3")

    // Logging
    implementation("org.tinylog:tinylog-impl:2.4.1")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha6")
    implementation("org.tinylog:slf4j-tinylog:2.4.1")

    // Native libraries
    supportedOS.map { "natives-$it" }.forEach { native ->
        runtimeOnly("org.lwjgl", "lwjgl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = native)
    }

    runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = "natives-macos")

    // Test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2") // Test
    testImplementation("org.assertj:assertj-core:3.22.0") // Assertions
    testImplementation("org.mockito:mockito-core:4.3.1") // Mocking
    testImplementation("org.mockito:mockito-junit-jupiter:4.3.1") // Mocking
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

    // Configure code coverage
    named<JacocoReport>("jacocoTestReport") {
        dependsOn("test")

        sourceSets(sourceSets.getByName("test"))

        reports {
            xml.required.set(true)
        }
    }

    named("build") {
        dependsOn("javadoc")
    }

    create("ci") {
        dependsOn("build")
        dependsOn("uploadCoverage")
    }
}

//tasks.registerUploadCoverageTask(project)