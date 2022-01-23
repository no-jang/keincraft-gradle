package coverage

import util.createDir

val codacyDir: Provider<Directory> = layout.buildDirectory.dir("tmp/codacy").createDir()

tasks.create<FetchCoverageUploaderTask>("fetchCoverageUploader") {
    workingDir.set(codacyDir)
}