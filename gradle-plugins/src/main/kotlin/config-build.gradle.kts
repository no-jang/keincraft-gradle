import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import proguard.gradle.ProGuardTask

plugins {
    id("com.github.johnrengelman.shadow")
}

configurations {
    create("proguard") {
        extendsFrom(configurations.getByName("compileOnly"))
    }

    create("proguard-dev") {
        extendsFrom(configurations.getByName("implementation"))
        extendsFrom(configurations.getByName("compileOnly"))
    }
}

tasks {
    named<Jar>("jar") {
        archiveClassifier.set("dev")
    }

    named<ShadowJar>("shadowJar") {
        group = "build"
        archiveClassifier.set("")
    }
}

setupProguardTask("proguardJar", "optimized", "proguard", "shadowJar")
setupProguardTask("proguardDevJar", "optimized-dev", "proguard-dev", "jar")

fun setupProguardTask(name: String, prefix: String, configuration: String, jarTaskName: String) {
    tasks.create<ProGuardTask>(name) {
        val jarTask = tasks.named(jarTaskName)

        val outputFileNameProvider = provider {
            "$buildDir/libs/${project.name}-${project.version}${if (prefix.isNotEmpty()) "-$prefix" else ""}"
        }

        val outputFileProvider = provider {
            file("${outputFileNameProvider.get()}.jar")
        }

        group = "build"

        inputs.files(jarTask)
        outputs.files(outputFileProvider)

        injars(jarTask)

        configuration("optimized.pro")

        optimizationpasses(10)
        overloadaggressively()
        repackageclasses()
        allowaccessmodification()

        verbose()

        doFirst {
            libraryjars(configurations.getByName(configuration).files)
            outjars(outputFileProvider.get())

            printmapping("${outputFileNameProvider.get()}.mapping")
        }
    }
}