//import task.registerUploadCoverageTask

plugins {
    java
    jacoco

    id("coverage.coverage")
    id("sync.sync-deps")

    id("org.lwjgl.plugin") version "0.0.20"
}

group = "keincraft"
version = "0.1.0-SNAPSHOT"

val supportedOS = listOf("linux", "macos", "windows")

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

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

/*
val natives by configurations.creating {
    isTransitive = false
*/
/*    resolutionStrategy {
        eachDependency {
            useTarget("${requested.group}:${requested.name}:${requested.version}:")

            addExternalModuleDependencyTo()
        }
    }*/
/*    resolutionStrategy.dependencySubstitution {
        all {
            val component = requested;
            if (component is ModuleComponentSelector) {
                useTarget(component.group + ":" + component.name + ":" + component.z)
            }
        }
    }*/
//}

/*fun DependencyHandler.native(dependencyNotation: Any) {
    val config = natives.name

    val providedNotation = if(dependencyNotation is Provider<*>) {
        dependencyNotation.get()
    } else {
        dependencyNotation
    }

    val dependencies = mutableListOf<Dependency>()

    if(providedNotation is Iterable<*>) {
        for(notation in providedNotation) {
            dependencies.add(create(providedNotation))
        }
    } else {
        dependencies.add(create(providedNotation))
    }

    for(dependency in dependencies) {
        add(config, dependency)

        for(os in supportedOS) {
            addExternalModuleDependencyTo(this, config, dependency.group!!, dependency.name,
                dependency.version, null, "native-$os", null, null)
        }
    }
}*/

/*fun DependencyHandler.native(dependencyNotation: Any) {
    val configuration = natives.name

    val notations = dependencyNotation.getProvided()
    val listedNotations = notations.getListed()

    for(notation in listedNotations) {
        val dependency = create(notation);
        //add(configuration, dependency)
        supportedOS.forEach { os ->
            addExternalModuleDependencyTo(this, configuration, dependency.group!!, dependency.name, dependency.version, null, "native-$os", null, null)
        }
    }
}

fun Any.getProvided(): Any {
    if(this is Provider<*>) return get();
    return this;
}

fun Any.getListed(): List<Any> {
    if(this is List<*>) {
        val list = mutableListOf<Any>()
        list.addAll(this as Collection<Any>)
        return list
    }
    return listOf(this);
}*/

dependencies {
    // Core
/*    implementation(libs.bundles.lwjgl)
    native(libs.bundles.lwjgl)*/
    //Lwjgl {
    //    allNatives = true
    //    implementation(Lwjgl.Preset.minimalOpenGL)
    //    implementation(Lwjgl.Preset.minimalVulkan)
    //}

    // Compile
    compileOnly(libs.checkerframework.qual)

    // DI
    implementation(libs.guice) {

    }

    // IO
    implementation(libs.joml)

    // Log
    implementation(libs.bundles.tinylog)

    // Test
    testImplementation(libs.bundles.junit)

    /*libs.bundles.lwjgl.get().forEach {
        supportedOS.forEach { os ->
            implementation(provider { it }) {
                artifact {
                    classifier = "native-$os"
                }
            }
        }
    }*/


/*    // Compile Only
    compileOnly("org.checkerframework:checker-qual:3.21.1") // Checker annotations

    implementation(platform("org.lwjgl:lwjgl-bom:3.3.1-SNAPSHOT")) // Lwjgl library versions

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
    testImplementation("org.mockito:mockito-junit-jupiter:4.3.1") // Mocking*/
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