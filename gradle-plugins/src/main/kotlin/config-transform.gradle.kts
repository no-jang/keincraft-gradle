setupDistJar("distDevJar", "transform-dev", arrayOf("compileOnly", "implementation"), "jar")
setupDistJar("distJar", "transform", arrayOf("compileOnly"), "shadowJar")