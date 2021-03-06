import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Core {
	private const val version = "1.7.0"//Use only stable versions because of not working preview
	private const val versionSplashscreen = "1.0.0-beta01"
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
