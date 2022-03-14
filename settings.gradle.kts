dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}
rootProject.name = "Unlim Storage"
include(":app")
include(":boxStorage")
include(":coreModels")
include(":coreUI")
include(":dropBoxStorage")
include(":googleStorage")
include(":localStorage")
include(":preferences")
include(":storage")
include(":storageRepositories")
