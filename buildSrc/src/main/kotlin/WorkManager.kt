import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

object WorkManager {
	private const val lib = "androidx.work:work"
	private const val version = "2.7.1"

	object Lib {
		const val runtime = "$lib-runtime-ktx:$version"
	}

	object Group {
		const val guava = "com.google.guava"
	}
}

fun Project.implementWorkManager() {
	dependencies {
		implement(WorkManager.Lib.runtime) {
			exclude(WorkManager.Group.guava)
		}
	}
}
