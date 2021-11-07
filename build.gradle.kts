plugins {
    java
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDir("minecraft-clone")
        }
    }
}

dependencies {
    // Lwjgl Wrapper (Graphics, Audio, Input, ...)
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.10.0")
}