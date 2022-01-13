package taskcontainers

import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskContainer

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
