package extensions

import org.gradle.api.file.DirectoryProperty

abstract class AioRootExtension {
    abstract val outputSrcDir: DirectoryProperty
    abstract val outputTestDir: DirectoryProperty
}