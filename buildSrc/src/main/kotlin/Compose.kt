import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultDependencyConstraint.strictly
import org.gradle.kotlin.dsl.dependencies

object Compose {
	private const val lib = "androidx.compose"
	const val version = "1.2.0-alpha08"
	private const val versionMaterial3 = "1.0.0-alpha10"

	object Lib {
		const val foundation = ".foundation:foundation:$version"
		const val material = "$lib.material:material:$version"
		const val material3 = "$lib.material3:material3:$versionMaterial3"
		const val materialIconsExtended = "$lib.material:material-icons-extended:$version"
		const val runtime = "$lib.runtime:runtime:$version"
		const val runtimeLivedata = "$lib.runtime:runtime-livedata:$version"
		const val ui = "$lib.ui:ui:$version"
		const val uiTooling = "$lib.ui:ui-tooling:$version"
	}
}

fun Project.implementCompose() {
	dependencies {
		implement(Compose.Lib.material)
		implement(Compose.Lib.materialIconsExtended)
		implement(Compose.Lib.runtime)
		implement(Compose.Lib.ui)
		implementUITooling()
	}
}

fun Project.implementComposeForModules() {
	dependencies {
		implement(Compose.Lib.material)
		implementUITooling()
	}
}

fun Project.implementUITooling() {
	dependencies {
		implement(Compose.Lib.uiTooling) {//TODO: remove when preview will working
			strictly("androidx.compose.ui", "ui-tooling", "1.0.0-beta09")//Help only in app module
		}
	}
}

fun Project.implementIcons() {
	dependencies {
		implement(Compose.Lib.materialIconsExtended)
	}
}

fun Project.implementMaterial3() {
	dependencies {
		implement(Compose.Lib.material3)
	}
}
