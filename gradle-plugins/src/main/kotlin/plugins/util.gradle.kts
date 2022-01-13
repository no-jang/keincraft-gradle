//import taskcontainers.createDependencySyncTask

/*
tasks.createDependencySyncTask("syncDependencies", "keincraft/libs", configurations.named("runtimeClasspath"))
tasks.createDependencySyncTask("syncTestDependencies", "keincraft-test/libs", provider {
    val runtimeClasspath = configurations.named("runtimeClasspath").get()
    val testRuntimeClasspath = configurations.named("testRuntimeClasspath").get()
    return@provider testRuntimeClasspath.minus(runtimeClasspath)
})*/
