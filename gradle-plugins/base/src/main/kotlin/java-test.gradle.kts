plugins {
    id("java-compile")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}