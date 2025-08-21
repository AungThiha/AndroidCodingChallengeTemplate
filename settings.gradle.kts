pluginManagement {
    includeBuild("build-logic")
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
    }
}

rootProject.name = "CodingChallengeTemplate"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(
    ":coroutines:core",
    ":coroutines:test"
)
include("design")
include(
    ":navigation:core",
    ":navigation:test",
)
include(
    "network"
)
include(
    ":operation:core",
)
include(
    ":myfeature:data",
    ":myfeature:domain",
    ":myfeature:presentation",
)
