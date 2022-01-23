package util

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import java.io.File

fun File.createIfNotExists(): File {
    if (isDirectory) {
        delete()
    }

    if (!exists()) {
        createNewFile()
    }

    return this
}

fun File.createDirIfNotExists(): File {
    if (!isDirectory) {
        delete()
    }

    if (!exists()) {
        mkdirs()
    }

    return this
}

fun Provider<Directory>.createDir(): Provider<Directory> = map {
    val file = it.asFile

    if (!file.isDirectory) {
        file.delete()
    }

    if (!file.exists()) {
        file.mkdirs()
    }

    it
}

fun Provider<RegularFile>.create(): Provider<RegularFile> = map {
    val file = it.asFile

    if (!file.isDirectory) {
        file.delete()
    }

    if (!file.exists()) {
        file.mkdirs()
    }

    it
}