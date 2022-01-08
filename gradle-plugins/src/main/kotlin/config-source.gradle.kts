plugins {
    java
}

sourceSets {
    main {
        java {
            srcDir("keincraft")

            include("engine/**")
            include("main/**")
        }

        resources {
            srcDir("keincraft")

            include("**.properties")
            include("resources/**")
        }
    }

    test {
        java {
            srcDir("keincraft-test")

            include("engine/**")
            include("main/**")
        }

        resources {
            srcDir("keincraft-test")

            exclude("engine/**")
        }
    }
}