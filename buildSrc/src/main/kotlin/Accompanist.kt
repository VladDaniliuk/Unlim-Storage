import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Accompanist {
	private const val lib = "com.google.accompanist:accompanist"
	private const val version = "0.24.1-alpha"

	object Lib {
		const val swiperefresh = "$lib-swiperefresh:$version"
		const val systemuicontroller = "$lib-systemuicontroller:$version"
		const val permissions = "$lib-permissions:$version"
		const val navigationAnimation = "$lib-navigation-animation:$version"
	}
}

fun Project.implementAccompanist() {
	dependencies {
		implement(Accompanist.Lib.swiperefresh)
		implement(Accompanist.Lib.systemuicontroller)
	}
}

fun Project.implementSwipeRefresh() {
	dependencies {
		implement(Accompanist.Lib.swiperefresh)
	}
}
