import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Accompanist {
	private const val lib = "com.google.accompanist:accompanist"
	private const val version = "0.24.7-alpha"

	object Lib {
		const val swiperefresh = "$lib-swiperefresh:$version"
		const val systemuicontroller = "$lib-systemuicontroller:$version"
		const val permissions = "$lib-permissions:$version"
		const val navigationMaterial = "$lib-navigation-material:$version"
		const val navigationAnimation = "$lib-navigation-animation:$version"
	}
}

fun Project.implementAccompanist() {
	dependencies {
		implement(Accompanist.Lib.swiperefresh)
		implement(Accompanist.Lib.systemuicontroller)
	}
}

fun Project.implementAccompanistNavigation() {
	dependencies {
		implement(Accompanist.Lib.navigationMaterial)
	}
}

fun Project.implementSwipeRefresh() {
	dependencies {
		implement(Accompanist.Lib.swiperefresh)
	}
}

fun Project.implementNavigationAnimation() {
	dependencies {
		implement(Accompanist.Lib.navigationAnimation)
	}
}
