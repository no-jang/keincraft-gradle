import org.gradle.kotlin.dsl.plugins.precompiled.PrecompiledScriptPlugins

plugins {
    `kotlin-dsl` apply false
    `kotlin-dsl-precompiled-script-plugins` apply false
}

subprojects {
    apply<KotlinDslPlugin>()
    apply<PrecompiledScriptPlugins>()

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}