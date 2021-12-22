import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Box {
	private const val version = "5.0.0"
	private const val lib = "com.box:box-android-sdk:"

	object Lib {
		const val androidSdk = "$lib$version"
	}
}

fun Project.implementBoxApi() {
	dependencies {
		implement(Box.Lib.androidSdk)
	}
}
