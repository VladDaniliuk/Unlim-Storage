import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object WorkManager {
	private const val lib = "androidx.work:work"
	private const val version = "2.8.0-alpha01"

	object Lib {
		const val runtime = "$lib-runtime-ktx:$version"
	}
}

fun Project.implementWorkManager() {
	dependencies {
		implement(WorkManager.Lib.runtime)
	}
}
