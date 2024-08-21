pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = uri("https://oss.sonatype.org/content/repositories/snapshots/"))
    }
}

rootProject.name = "WriterMe"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":network")
include(":resources")
include(":features:onboarding")
include(":database")
include(":features:home")
