plugins {
    java

    id("com.github.spotbugs") version "5.0.3"
}

configurations {
    //create("checkerframework")
    //create("spotbugsPlugins")
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

sourceSets {
    main {
        java {
            srcDir("keincraft")

            exclude("**.properties")
            exclude("shaders/**")
            exclude("textures/**")
        }

        resources {
            srcDir("keincraft")

            include("**.properties")
            include("shaders/**")
            include("textures/**")
        }
    }
}

val nativeOS = arrayOf("natives-linux", "natives-macos", "natives-windows")

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:3.3.0-SNAPSHOT")) // Lwjgl Library Versions

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

    // Util
    implementation("org.joml:joml:1.10.2") // Math
    implementation("org.tinylog:tinylog-impl:2.4.1") // Logging
    implementation("org.tinylog:slf4j-tinylog:2.4.1")

    compileOnly("org.checkerframework:checker-qual:3.20.0")
    //implementation("com.github.spotbugs:spotbugs-annotations:4.5.2")

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

    spotbugs("com.github.spotbugs:spotbugs:4.5.2")
    //"checkerframework"("org.checkerframework:checker:3.21.0")
}

spotbugs {
    showProgress.set(true)
}

tasks.create<Sync>("copyLibraries") {
    from(configurations.runtimeClasspath)
    into("keincraft/libs")
    preserve {
        include("*.txt")
    }
}

/*tasks.create<JavaCompile>("checkerframework") {
    group = "verification"
    val sourceSet = sourceSets.getByName("main")
    source = sourceSet.java
    destinationDirectory.set(sourceSet.java.classesDirectory)
    classpath = sourceSet.compileClasspath + sourceSet.output.classesDirs
    options.annotationProcessorPath = configurations.getByName("checkerframework")
    options.compilerArgs.addAll(listOf(
        "-proc:only",
        "-processor",
        "org.checkerframework.checker.calledmethods.CalledMethodsChecker," +
                //"org.checkerframework.checker.compilermsgs.CompilerMessagesChecker," +
                //"org.checkerframework.checker.fenum.FenumChecker," +
                //"org.checkerframework.checker.formatter.FormatterChecker," +
                //"org.checkerframework.checker.i18n.I18nChecker," +
                //"org.checkerframework.checker.i18nformatter.I18nFormatterChecker," +
                "org.checkerframework.checker.index.IndexChecker," +
                "org.checkerframework.checker.interning.InterningChecker," +
                //"org.checkerframework.checker.lock.LockChecker," +
                "org.checkerframework.checker.mustcall.MustCallChecker," +
                "org.checkerframework.checker.nullness.NullnessChecker," +
                ///"org.checkerframework.checker.optional.OptionalChecker," +
                //"org.checkerframework.checker.propkey.PropertyKeyChecker," +
                //"org.checkerframework.checker.regex.RegexChecker," +
                "org.checkerframework.checker.resourceleak.ResourceLeakChecker," +
                "org.checkerframework.checker.signature.SignatureChecker," +
                "org.checkerframework.checker.signedness.SignednessChecker",
                //"org.checkerframework.checker.tainting.TaintingChecker," +
                //"org.checkerframework.checker.units.UnitsChecker",
        "-Alint",
        "-Xlint",
        "-AwarnUnneededSuppressions",
        "-Xmaxerrs", "10000",
        "-Xmaxwarns", "10000"
    ))
}*/

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    reports {
        create("html") {
            required.set(true)
        }
    }
}