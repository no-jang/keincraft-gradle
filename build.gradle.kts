import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java

    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("proguard") apply false
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
}

// Dev jar containing only the compiled project
val jarTask = tasks.named<Jar>("jar") {
    archiveClassifier.set("dev")
}

// All (shadow) jar containing dev jar + every library
val shadowJarTask = tasks.named<ShadowJar>("shadowJar") {
    group = "build"
    archiveClassifier.set("all")
}

/*
val dirJarTask: TaskProvider<Task> = tasks.named("distJar")

val startTransformScriptsTask = tasks.register<CreateStartScripts>("startTransformScripts") {
    group = "distribution"
    classpath = files(dirJarTask)
    applicationName = project.name
    outputDir = buildDir.resolve("scriptsTransform")
}

distributions {
    named("main") {
        distributionBaseName.set(project.name)
        contents {
            into("bin") {
                from(tasks.named("startScripts"))
                fileMode = 493
            }

            into("lib") {
                from(dirJarTask)
            }
        }
    }

    named("windows") {
        distributionBaseName.set(project.name)
        contents {
            into("bin") {
                from(tasks.named("startScripts"))
                fileMode = 493
            }

            into("lib") {
                from(transformJarTask)
            }
        }
    }
}

tasks.forEach {
    if(it.name.contains("shadow", true) && (it.group == "distribution" || it.group == "")) {
        it.enabled = false
        it.group = "disabled"
    }
}*/

setupDistJar("distDevJar", "dist-dev", arrayOf("compileOnly", "implementation"), jarTask)
setupDistJar("distJar", "", arrayOf("compileOnly"), shadowJarTask)

// Create one dist jar per operating system where only needed system specific library files will be placed
for (os in supportedOS) {
    tasks.create<Jar>("${os}DistJar") {
        group = "distribution"
        from(
            zipTree(
                tasks.named<proguard.gradle.ProGuardTask>("distJar")
                    .get().outJarFileCollection
            )
                .files.first()
        )
        supportedOS.filter { it != os }.forEach {
            exclude(it)
        }
    }
}

fun setupDistJar(name: String, prefix: String, configs: Array<String>, jarTask: TaskProvider<out Task>) {
    configurations.create(name) {
        for (configuration in configs) {
            extendsFrom(configurations.getByName(configuration))
        }
    }

    tasks.create<proguard.gradle.ProGuardTask>(name) {
        val outputNameProvider = provider {
            "$buildDir/libs/${project.name}-${project.version}${if (prefix.isNotEmpty()) "-$prefix" else ""}"
        }

        val outputJarProvider = provider {
            file("${outputNameProvider.get()}.jar")
        }

        val outputMappingProvider = provider {
            file("${outputNameProvider.get()}.mapping")
        }

        group = "distribution"

        inputs.files(jarTask)
        outputs.files(outputJarProvider)

        injars(jarTask)
        outjars(outputJarProvider)

        configuration("transform.pro")

        optimizationpasses(10)
        overloadaggressively()
        repackageclasses()
        allowaccessmodification()

        verbose()

        doFirst {
            libraryjars(configurations.getByName(name).files)

            printmapping(outputMappingProvider)
        }
    }
}