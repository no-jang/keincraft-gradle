plugins {
    id("aio")
    id("java-default")
}

dependencies {

}

aio {
    srcPackage("engine/common")
    testPackage("engine/common")
}

/*
aio {
    mainDirAsInput()
    testDirAsInput()
}*/
