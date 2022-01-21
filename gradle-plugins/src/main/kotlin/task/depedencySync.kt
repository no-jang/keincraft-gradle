package task

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskContainer

fun TaskContainer.registerDependencySyncTasks(project: Project) {
    val configurations = project.configurations

    createDependencySyncTask("syncDependencies", "keincraft/libs", project.provider {
        val runtimeClasspath = configurations.named("runtimeClasspath").get()
        val compileClasspath = configurations.named("compileClasspath").get()
        return@provider runtimeClasspath.plus(compileClasspath)
    })

    createDependencySyncTask("syncTestDependencies", "keincraft-test/libs", project.provider {
        val runtimeClasspath = configurations.named("runtimeClasspath").get()
        val compileClasspath = configurations.named("compileClasspath").get()
        val testRuntimeClasspath = configurations.named("testRuntimeClasspath").get()
        val testCompileClasspath = configurations.named("testRuntimeClasspath").get()
        return@provider testRuntimeClasspath.plus(testCompileClasspath).minus(runtimeClasspath).minus(compileClasspath)
    })
}

fun TaskContainer.createDependencySyncTask(
    name: String,
    destinationDirectory: String,
    dependencyConfiguration: Provider<out FileCollection>,
) {
    create(name, Sync::class.java).apply {
        into(destinationDirectory)
        from(dependencyConfiguration)

        preserve {
            include("*.txt")
        }
    }
}
