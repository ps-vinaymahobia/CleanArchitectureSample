pluginManagement {
    includeBuild("gradle-dependencies") //module contains build logic
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CleanArchitectureSample"
include(":app")
include(":ui")
include(":domain")
include(":data")
include(":presentation")
