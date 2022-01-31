import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Activity {
	private const val lib = "androidx.activity:activity"
	private const val version = "1.5.0-alpha01"

	object Lib {
		const val compose = "$lib-compose:$version"
	}
}

fun Project.implementActivity() {
	dependencies {
		implement(Activity.Lib.compose)
	}
}
