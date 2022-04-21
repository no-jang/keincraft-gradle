plugins {
    id("java-jar")
}

tasks.create<Jar>("apiJar") {
    group = "build"
    archiveClassifier.set("api")
    from(sourceSets.getByName("main").output)
    include("**/**/**/api/**")
    include("module-info.class")
}

tasks.create<Jar>("apiSourcesJar") {
    group = "build"
}

tasks.create<Jar>("apiJavadocJar") {
    group = "build"
}