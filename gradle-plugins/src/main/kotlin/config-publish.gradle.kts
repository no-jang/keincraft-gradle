import proguard.gradle.ProGuardTask

plugins {
    application

    id("proguard")
}

tasks {
    named("startScripts") {
        group = "distribution"
    }
}

/*
val transformJarTask: TaskProvider<Task> = tasks.named("transformJar")

val startTransformScriptsTask = tasks.register<CreateStartScripts>("startTransformScripts") {
    classpath = files(transformJarTask)
    applicationName = project.name
    outputDir = buildDir.resolve("scriptsTransform")
}

distributions {
    named("main") {
        distributionBaseName.set(project.name)
        contents {
            into("bin") {
                from(tasks.named("startScripts"))
                fileMode = 493
            }

            into("lib") {
                from(transformJarTask)
            }
        }
    }

    remove("shadow")

*/
/*    named("shadow") {
        distributionBaseName.set("${project.name}-all")
    }

    create("transform") {
        distributionBaseName.set(project.name)
        contents {
            into("bin") {
                from(startTransformScriptsTask)
                fileMode = 493
            }

            into("lib") {
                from(transformJarTask)
            }
        }
    }*//*

}*/

fun setupDistJar(name: String, prefix: String, configs: Array<String>, jarTaskName: String) {
    configurations.create(name) {
        for (configuration in configs) {
            extendsFrom(configurations.getByName(configuration))
        }
    }

    tasks.create<ProGuardTask>(name) {
        val jarTask = tasks.named(jarTaskName)

        val outputNameProvider = provider {
            "$buildDir/libs/${project.name}-${project.version}${if (prefix.isNotEmpty()) "-$prefix" else ""}"
        }

        val outputJarProvider = provider {
            file("${outputNameProvider.get()}.jar")
        }

        val outputMappingProvider = provider {
            file("${outputNameProvider.get()}.mapping")
        }

        group = "distribution"

        inputs.files(jarTask)
        outputs.files(outputJarProvider)

        injars(jarTask)
        outjars(outputJarProvider)

        configuration("transform.pro")

        optimizationpasses(10)
        overloadaggressively()
        repackageclasses()
        allowaccessmodification()

        verbose()

        doFirst {
            libraryjars(configurations.getByName(name).files)

            printmapping(outputMappingProvider)
        }
    }
}