import extensions.AioExtension
import extensions.AioRootExtension

val rootExtension = rootProject.extensions.getByType(AioRootExtension::class)
val extension: AioExtension = extensions.create("aio")

/*
afterEvaluate {
    tasks.create<Sync>("syncSrcAIO") {
        extension.srcMappings.get().forEach { mapping ->
            from(mapping.path) {
                into(rootExtension.outputSrcDir.dir(mapping.prefix))
            }
        }
    }

    tasks.create<Sync>("syncTestAIO") {
        extension.testMappings.get().forEach { mapping ->
            from(mapping.path) {
                into(rootExtension.outputTestDir.dir(mapping.prefix))
            }
        }
    }
}
*/

/*
afterEvaluate {
    tasks.create<Sync>("syncSourceAIO") {
        into(rootExtension.outputDir)

        extension.sourceDirs.get().forEach { inputDir ->
            from(inputDir)
        }

        exclude("module-info.java")
    }

    afterEvaluate {
        tasks.create<Sync>("syncTestAIO") {
            into(rootExtension.outputDir)

            extension.testDirs.get().forEach { inputDir ->
                from(inputDir)
            }

            exclude("module-info.java")
        }
    }
}*/
