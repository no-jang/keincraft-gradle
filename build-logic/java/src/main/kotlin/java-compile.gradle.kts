plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile>() {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}