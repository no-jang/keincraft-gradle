import extensions.AioRootExtension

val extension: AioRootExtension = extensions.create("aio")

/*println(subprojects.mapNotNull { it.plugins.findPlugin("aio") })

evaluationDependsOnChildren()

afterEvaluate {
    val childExtensions = subprojects.mapNotNull { subproject ->
        subproject.extensions.findByType(AioExtension::class)
    }

    tasks.create<Sync>("syncSourceAIO") {
        into(extension.outputSourceDir)

        childExtensions.forEach {
            from(it.sourceDirs)
        }

        exclude("module-info.java")
    }

    tasks.create<Sync>("syncTestAIO") {
        into(extension.outputTestDir)

        childExtensions.forEach {
            from(it.testDirs)
        }

        exclude("module-info.java")
    }
}*/

//afterEvaluate {
//  val subprojects = subprojects
//val childExtensions = mutableListOf<AioExtension>()

//subprojects.forEach { subproject ->
//subproject.afterEvaluate {
//val extension = extensions.findByType(AioExtension::class)
//if(extension != null) {
//    childExtensions.add(extension)
//}
//}
//}
//}