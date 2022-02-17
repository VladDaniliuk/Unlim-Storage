import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

object Modules {
	const val storage = ":storage"
	const val coreModels = ":coreModels"
}

fun Project.implementModules() {
	dependencies {
		implement(project(Modules.storage))
		implement(project(Modules.coreModels))
	}
}

fun Project.implementModulesForStorage() {
	dependencies {
		implement(project(Modules.coreModels))
	}
}
