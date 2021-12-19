import proguard.gradle.ProGuardTask

plugins {
    id("com.github.johnrengelman.shadow")
}

configurations {
    create("proguard") {
        extendsFrom(configurations.getByName("implementation"))
        extendsFrom(configurations.getByName("compileOnly"))
    }

    create("proguard-all") {
        extendsFrom(configurations.getByName("compileOnly"))
    }
}

setupProguardTask("proguard", "jar")
setupProguardTask("proguard-all", "shadowJar")

fun setupProguardTask(type: String, jarTaskName: String) {
    tasks.create<ProGuardTask>(type) {
        val jarTask = tasks.named(jarTaskName)
        val outputFileName = "$buildDir/libs/$name-$version-$type"
        val outputFileJar = "$outputFileName.jar"

        group = "build"

        inputs.files(jarTask)
        outputs.files(outputFileJar)

        injars(jarTask)
        outjars(outputFileJar)

        configuration("optimized.pro")

        printmapping("$outputFileName.mapping")

        optimizationpasses(5)
        overloadaggressively()
        repackageclasses()
        allowaccessmodification()

        verbose()

        doFirst {
            libraryjars(configurations.getByName(type).files)
        }
    }
}