import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Lifecycle {
	private const val lib = "androidx.lifecycle:lifecycle"
	private const val version = "2.5.0-alpha05"

	object Lib {
		const val viewmodelCompose = "$lib-viewmodel-compose:$version"
	}
}

fun Project.implementLifecycle() {
	dependencies {
		implement(Lifecycle.Lib.viewmodelCompose)
	}
}
