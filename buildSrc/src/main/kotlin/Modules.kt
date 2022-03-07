import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

object Modules {
	const val boxStorage = ":boxStorage"
	const val coreModels = ":coreModels"
	const val dropBoxStorage = ":dropBoxStorage"
	const val googleStorage = ":googleStorage"
	const val preferences = ":preferences"
	const val storage = ":storage"
	const val localStorage = ":localStorage"
	const val storageRepositories = ":storageRepositories"
}

fun Project.implementModules() {
	dependencies {
		implement(project(Modules.boxStorage))
		implement(project(Modules.coreModels))
		implement(project(Modules.dropBoxStorage))
		implement(project(Modules.googleStorage))
		implement(project(Modules.localStorage))
		implement(project(Modules.preferences))
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

fun Project.implementModulesForLocalStorage() {
	dependencies {
		implement(project(Modules.coreModels))
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
