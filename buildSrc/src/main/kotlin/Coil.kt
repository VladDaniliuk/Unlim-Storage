import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Coil {
	private const val lib = "io.coil-kt:coil-compose:"
	private const val version = "2.0.0-alpha02"

	object Lib {
		const val compose = "$lib$version"
	}
}

fun Project.implementCoil() {
	dependencies {
		implement(Coil.Lib.compose)
	}
}
