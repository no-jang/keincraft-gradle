rootProject.name = "keincraft"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    //includeBuild("gradle-plugins")
}

includeBuild("gradle-plugins")

include("engine-api")
include("engine-client")
include("engine-common")
include("engine-core")
include("engine-server")
include("keincraft-api")
include("keincraft-client")
include("keincraft-common")
include("keincraft-core")
include("keincraft-main")
include("keincraft-server")