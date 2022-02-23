plugins {
    id("aio-root")
}

allprojects {
    group = "keincraft"
    version = "0.1.0-SNAPSHOT"
}

aio {
    outputSrcDir.set(layout.projectDirectory.dir("aio"))
    outputTestDir.set(layout.projectDirectory.dir("aio-test"))
}