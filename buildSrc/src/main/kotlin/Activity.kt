import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Activity {
	private const val lib = "androidx.activity:activity"

	//1.5.0-alpha02 ERROR with dagger https://github.com/google/dagger/issues/3226
	private const val version = "1.4.0"

	object Lib {
		const val compose = "$lib-compose:$version"
	}
}

fun Project.implementActivity() {
	dependencies {
		implement(Activity.Lib.compose)
	}
}
