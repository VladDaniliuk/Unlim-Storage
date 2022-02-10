import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Biometric {
	private const val version = "1.2.0-alpha04"
	private const val lib = "androidx.biometric:biometric-ktx:"

	object Lib {
		const val biometric = "$lib$version"
	}
}

fun Project.implementBiometric() {
	dependencies {
		implement(Biometric.Lib.biometric)
	}
}
