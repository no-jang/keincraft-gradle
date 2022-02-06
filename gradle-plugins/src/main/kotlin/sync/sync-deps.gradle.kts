package sync

plugins {
    java
}

afterEvaluate {
    tasks.create<Sync>("syncDependencies") {
        into(file("keincraft-deps"))

        sourceSets.forEach { sourceSet ->
            from(configurations.getByName(sourceSet.compileClasspathConfigurationName))
            from(configurations.getByName(sourceSet.runtimeClasspathConfigurationName))
        }

        preserve {
            include("**.txt")
        }
    }
}