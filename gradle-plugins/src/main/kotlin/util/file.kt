package util

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