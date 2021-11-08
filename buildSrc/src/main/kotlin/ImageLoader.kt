import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Coil {
	private const val version = "2.0.0-alpha02"
	private const val lib = "io.coil-kt:coil"

	object Lib {
		const val compose = "$lib-compose:$version"
	}
}

fun Project.implementImageLoader() {
	dependencies {
		add("implementation", Coil.Lib.compose)
	}
}
