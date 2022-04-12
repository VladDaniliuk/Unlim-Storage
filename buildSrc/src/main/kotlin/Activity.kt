import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Activity {
	private const val lib = "androidx.activity:activity"

	private const val version = "1.4.0"//Use only stable versions because of not working preview

	object Lib {
		const val compose = "$lib-compose:$version"
	}
}

fun Project.implementActivity() {
	dependencies {
		implement(Activity.Lib.compose)
	}
}
