package task

/*
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import util.createDirIfNotExists
import util.getLatestCommitUUID
import java.io.File

fun TaskContainer.registerUploadCoverageTask(project: Project) {
    create("uploadCoverage").apply {
        dependsOn(project.tasks.getByName("jacocoTestReport"))

        doFirst {
            val workingDir = project.file("${project.buildDir}/tmp/codacy").createDirIfNotExists()

            downloadCoverageUploader(project, workingDir)

            uploadCoverage(project, workingDir, "keincraft", "keincraft")
            uploadCoverage(project, workingDir, "keincraft-test", "keincraftTest")
        }
    }
}

fun uploadCoverage(project: Project, workingDir: File, submoduleName: String, projectTokenPrefix: String) {
    val projectToken = project.providers.systemProperty("${projectTokenPrefix}CodacyToken")

    if (!projectToken.isPresent) {
        throw GradleException("To upload the coverage you must provide a codacy project token with -D${projectTokenPrefix}CodacyToken=TOKEN")
    }

    val reportFile = project.file("${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")
    val lastCommitUUID = getLatestCommitUUID(project, submoduleName)

    project.exec {
        workingDir(workingDir)
        commandLine(
            "bash get.sh report -r ${reportFile.absolutePath} --project-token ${projectToken.get()} --commit-uuid $lastCommitUUID".split(
                " "
            )
        )
    }
}

fun downloadCoverageUploader(project: Project, workingDir: File) {
    project.exec {
        workingDir(workingDir)
        commandLine("wget -O get.sh https://coverage.codacy.com/get.sh".split(" "))
    }
}*/
