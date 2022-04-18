import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

object Modules {
	const val autoUpdateFeature = ":autoUpdateFeature"
	const val boxStorage = ":boxStorage"
	const val coreModels = ":coreModels"
	const val coreUI = ":coreUI"
	const val coreUtils = ":coreUtils"
	const val dropBoxStorage = ":dropBoxStorage"
	const val filesFeature = ":filesFeature"
	const val googleStorage = ":googleStorage"
	const val localStorage = ":localStorage"
	const val preferences = ":preferences"
	const val settingsFeature = ":settingsFeature"
	const val signInFeature = ":signInFeature"
	const val storage = ":storage"
	const val storageRepositories = ":storageRepositories"
}

fun Project.implementModules() {
	dependencies {
		implement(project(Modules.autoUpdateFeature))
		implement(project(Modules.coreModels))
		implement(project(Modules.coreUI))
		implement(project(Modules.coreUtils))
		implement(project(Modules.filesFeature))
		implement(project(Modules.preferences))
		implement(project(Modules.settingsFeature))
		implement(project(Modules.signInFeature))
		implement(project(Modules.storage))
		implement(project(Modules.storageRepositories))
	}
}

fun Project.implementModulesForChildStorage() {
	dependencies {
		implement(project(Modules.storage))
		implement(project(Modules.coreModels))
	}
}

fun Project.implementModulesForDropBoxStorage() {
	dependencies {
		implement(project(Modules.coreModels))
		implement(project(Modules.preferences))
		implement(project(Modules.storage))
	}
}

fun Project.implementModulesForCoreUI() {
	dependencies {
		implement(project(Modules.preferences))
		implement(project(Modules.coreUtils))
	}
}

fun Project.implementModulesForFilesFeature() {
	dependencies {
		implement(project(Modules.coreModels))
		implement(project(Modules.coreUI))
		implement(project(Modules.coreUtils))
		implement(project(Modules.storageRepositories))
	}
}

fun Project.implementModulesForLocalStorage() {
	dependencies {
		implement(project(Modules.coreModels))
	}
}

fun Project.implementModulesForSignInFeature() {
	dependencies {
		implement(project(Modules.coreModels))
		implement(project(Modules.coreUI))
		implement(project(Modules.coreUtils))
		implement(project(Modules.storageRepositories))
		implement(project(Modules.preferences))
	}
}

fun Project.implementModulesForSettingsFeature() {
	dependencies {
		implement(project(Modules.coreUI))
		implement(project(Modules.coreUtils))
		implement(project(Modules.preferences))
	}
}

fun Project.implementModulesForStorage() {
	dependencies {
		implement(project(Modules.coreModels))
	}
}

fun Project.implementModulesForStorageRepositories() {
	dependencies {
		implement(project(Modules.boxStorage))
		implement(project(Modules.coreModels))
		implement(project(Modules.dropBoxStorage))
		implement(project(Modules.googleStorage))
		implement(project(Modules.localStorage))
		implement(project(Modules.storage))
	}
}

fun Project.implementModulesForAutoUpdateFeature() {
	dependencies {
		implement(project(Modules.coreModels))
		implement(project(Modules.coreUI))
		implement(project(Modules.coreUtils))
		implement(project(Modules.preferences))
	}
}
