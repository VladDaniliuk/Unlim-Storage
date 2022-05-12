import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Navigation {
	private const val lib = "androidx.navigation:navigation"
	private const val version = "2.5.0-rc01"

	object Lib {
		const val compose = "$lib-compose:$version"
		const val fragmentKtx = "$lib-fragment-ktx:$version"
		const val uiKtx = "$lib-ui-ktx:$version"
	}
}

fun Project.implementNavigation() {
	dependencies {
		implement(Navigation.Lib.compose)
		implement(Navigation.Lib.uiKtx)
	}
}
