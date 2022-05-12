import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Activity {
	private const val lib = "androidx.activity:activity"

	private const val version = "1.6.0-alpha03"

	object Lib {
		const val compose = "$lib-compose:$version"
	}
}

fun Project.implementActivity() {
	dependencies {
		implement(Activity.Lib.compose)
	}
}
