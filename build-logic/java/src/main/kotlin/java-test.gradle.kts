plugins {
    id("java-compile")
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation("com.google.truth:truth:1.1.3")
}

tasks.named<Test>("test") {
    setWorkingDir(layout.buildDirectory.dir("test"))
    useJUnitPlatform()
    doFirst {
        workingDir.mkdirs()
    }
}