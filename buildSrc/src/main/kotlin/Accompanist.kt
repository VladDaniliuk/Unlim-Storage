import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Accompanist {
	private const val library = "com.google.accompanist:accompanist"
	private const val version = "0.21.0-beta"

	object Lib {
		const val swiperefresh = "$library-swiperefresh:$version"
		const val systemuicontroller = "$library-systemuicontroller:$version"
		const val insets = "$library-insets:$version"
		const val permissions = "$library-permissions:$version"
	}
}

fun Project.implementAccompanist() {
	dependencies {
		add("implementation", Accompanist.Lib.swiperefresh)
		add("implementation", Accompanist.Lib.insets)
		add("implementation", Accompanist.Lib.systemuicontroller)
		add("implementation", Accompanist.Lib.permissions)
	}
}
