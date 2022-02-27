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
include(":googleStorage")
include(":preferences")
include(":storage")
