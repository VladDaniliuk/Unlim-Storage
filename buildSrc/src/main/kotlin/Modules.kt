import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

object Modules {
	const val boxStorage = ":boxStorage"
	const val coreModels = ":coreModels"
	const val googleStorage = ":googleStorage"
	const val preferences = ":preferences"
	const val storage = ":storage"
}

fun Project.implementModules() {
	dependencies {
		implement(project(Modules.boxStorage))
		implement(project(Modules.coreModels))
		implement(project(Modules.googleStorage))
		implement(project(Modules.preferences))
		implement(project(Modules.storage))
	}
}

fun Project.implementModulesForStorage() {
	dependencies {
		implement(project(Modules.coreModels))
	}
}

fun Project.implementModulesForChildStorage() {
	dependencies {
		implement(project(Modules.storage))
		implement(project(Modules.coreModels))
	}
}
