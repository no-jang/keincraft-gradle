package coverage

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import task.BaseTask

abstract class FetchCoverageUploaderTask : BaseTask() {
    @get:InputDirectory
    abstract val workingDir: DirectoryProperty

    @TaskAction
    fun run() {
        val outputFile = workingDir.file("get.sh").get()

        project.exec {
            workingDir(workingDir)
            commandLine("wget -O $outputFile https://coverage.codacy.com/get.sh".split(" "))
        }
    }
}