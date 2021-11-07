plugins {
    java
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("minecraft-clone")
    }
}

val lwjglNatives = arrayOf("natives-linux", "natives-linux-arm32", "natives-linux-arm64", "natives-macos", "natives-windows", "natives-windows-x86")

dependencies {
    // LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:3.2.3"))

    implementation("org.lwjgl", "lwjgl-stb") // Textures

    // Native libraries
    lwjglNatives.forEach { native ->
        runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = native)
    }

    // Lwjgl Wrapper (Graphics, Audio, Input, ...)
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.10.0")

    // Math
    implementation("org.joml:joml:1.10.2")
}