package sync

tasks {
    create<Sync>("syncDeps") {
        into("keincraft/libs")
        from(configurations.named("runtimeClasspath"))
        from(configurations.named("compileClasspath"))

        preserve {
            include("**.txt")
        }
    }

    create<Sync>("syncTestDeps") {
        into("keincraft-test/libs")
        from(provider {
            val configuration = configurations.named("runtimeClasspath").get()
            val testConfiguration = configurations.named("testRuntimeClasspath").get()
            testConfiguration.minus(configuration)
        })

        from(provider {
            val configuration = configurations.named("compileClasspath").get()
            val testConfiguration = configurations.named("testCompileClasspath").get()
            testConfiguration.minus(configuration)
        })

        preserve {
            include("**.txt")
        }
    }
}