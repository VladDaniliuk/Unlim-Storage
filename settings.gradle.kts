dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}
rootProject.name = "Unlim Storage"
include(":app")
include(":autoUpdateFeature")
include(":boxStorage")
include(":coreModels")
include(":coreUI")
include(":coreUtils")
include(":dropBoxStorage")
include(":filesFeature")
include(":googleStorage")
include(":localStorage")
include(":preferences")
include(":settingsFeature")
include(":signInFeature")
include(":storage")
include(":storageRepositories")
