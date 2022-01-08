plugins {
    application
}

application {
    mainClass.set("Main")
}

val proguardJarTask: TaskProvider<Task> = tasks.named("proguardJar")

val startOptimizedScriptsTask = tasks.register<CreateStartScripts>("startOptimizedScripts") {
    classpath = files(proguardJarTask)
    applicationName = project.name
    outputDir = buildDir.resolve("scriptsOptimized")
}

distributions {
    named("main") {
        distributionBaseName.set("${project.name}-dev")
    }

    named("shadow") {
        distributionBaseName.set(project.name)
    }

    create("optimized") {
        distributionBaseName.set("${project.name}-optimized")
        contents {
            into("bin") {
                from(startOptimizedScriptsTask)
                fileMode = 493
            }

            into("lib") {
                from(proguardJarTask)
            }
        }
    }
}