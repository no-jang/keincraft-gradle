plugins {
    id("java-test")
}

dependencies {
    api(projects.engine.log)
    implementation(libs.tinylog.core)
    implementation(libs.tinylog.slf4j)
}