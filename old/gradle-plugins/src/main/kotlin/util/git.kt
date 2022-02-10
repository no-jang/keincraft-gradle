package util

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

fun getLatestCommitUUID(project: Project, submoduleName: String): String {
    return ByteArrayOutputStream().use { outputStream ->
        project.exec {
            workingDir(project.projectDir)
            commandLine("git rev-parse HEAD:$submoduleName".split(" "))
            standardOutput = outputStream
        }

        outputStream.toString()
    }
}