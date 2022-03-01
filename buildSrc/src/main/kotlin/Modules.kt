import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

object Modules {
	object BoxStorage {
		const val name = ":boxStorage"
		const val nameSpace = "com.shov.boxstorage"
	}

	object CoreModels {
		const val name = ":coreModels"
		const val nameSpace = "com.shov.coremodels"
	}

	object DropBoxStorage {
		const val name = ":dropBoxStorage"
		const val nameSpace = "com.shov.dropboxstorage"
	}

	object GoogleStorage {
		const val name = ":googleStorage"
		const val nameSpace = "com.shov.googlestorage"
	}

	object Preferences {
		const val name = ":preferences"
		const val nameSpace = "com.shov.preferences"
	}

	object Storage {
		const val name = ":storage"
		const val nameSpace = "com.shov.storage"
	}
}

fun Project.implementModules() {
	dependencies {
		implement(project(Modules.BoxStorage.name))
		implement(project(Modules.CoreModels.name))
		implement(project(Modules.DropBoxStorage.name))
		implement(project(Modules.GoogleStorage.name))
		implement(project(Modules.Preferences.name))
		implement(project(Modules.Storage.name))
	}
}

fun Project.implementModulesForStorage() {
	dependencies {
		implement(project(Modules.CoreModels.name))
	}
}

fun Project.implementModulesForChildStorage() {
	dependencies {
		implement(project(Modules.Storage.name))
		implement(project(Modules.CoreModels.name))
	}
}

fun Project.implementModulesForDropBoxStorage() {
	dependencies {
		implement(project(Modules.CoreModels.name))
		implement(project(Modules.Preferences.name))
		implement(project(Modules.Storage.name))
	}
}
