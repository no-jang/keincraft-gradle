plugins {
    application
}

application {
    mainClass.set("main.Main")
}

tasks {
    named("startShadowScripts") {
        group = ""
    }

    named<JavaExec>("run") {
        val dir = file("run/keincraft-dev")
        if (!dir.exists()) dir.mkdirs()
        workingDir(dir)
    }

    named<JavaExec>("runShadow") {
        val dir = file("run/keincraft-all")
        if (!dir.exists()) dir.mkdirs()
        workingDir(dir)
    }

    create<JavaExec>("runDist") {
        group = "application"

        val dir = file("run/keincraft")
        if (!dir.exists()) dir.mkdirs()
        workingDir(dir)
        mainClass.set(application.mainClass)
        classpath(named("distJar"))
    }
}