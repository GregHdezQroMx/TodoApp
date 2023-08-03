pluginManagement {
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
    // Agrega el catálogo de versiones
    versionCatalogs {
        create("local") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "TodoApp"
include(":app")
 