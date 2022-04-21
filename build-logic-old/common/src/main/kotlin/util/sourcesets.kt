package util

import org.gradle.api.Project

fun Project.apiOnly(action: Project.() -> Unit) {
    if (plugins.hasPlugin("basic-api")) {
        action()
    }
}