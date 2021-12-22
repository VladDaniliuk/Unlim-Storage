import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Core {
	private const val version = "1.6.0"
	private const val versionSplashscreen = "1.0.0-alpha02"
	private const val lib = "androidx.core:core"

	object Lib {
		const val ktx = "$lib-ktx:$version"
		const val splashscreen = "$lib-splashscreen:$versionSplashscreen"
	}
}

fun Project.implementCore() {
	dependencies {
		implement(Core.Lib.ktx)
		implement(Core.Lib.splashscreen)
	}
}
