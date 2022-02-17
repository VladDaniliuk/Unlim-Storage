import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Compose {
	private const val lib = "androidx.compose"
	const val version = "1.2.0-alpha02"
	private const val versionMaterial3 = "1.0.0-alpha04"

	object Lib {
		const val material = "$lib.material:material:$version"
		const val material3 = "$lib.material3:material3:$versionMaterial3"
		const val materialIconsExtended = "$lib.material:material-icons-extended:$version"
		const val runtime = "$lib.runtime:runtime:$version"
		const val runtimeLivedata = "$lib.runtime:runtime-livedata:$version"
		const val ui = "$lib.ui:ui:$version"
		const val uiTooling = "$lib.ui:ui-tooling:$version"
	} //TODO migrate to material3
}//TODO color scheme of material3

fun Project.implementCompose() {
	dependencies {
		implement(Compose.Lib.material)
		implement(Compose.Lib.materialIconsExtended)
		implement(Compose.Lib.runtime)
		implement(Compose.Lib.ui)
		implement(Compose.Lib.uiTooling)
	}
}

fun Project.implementIcons() {
	dependencies {
		implement(Compose.Lib.materialIconsExtended)
	}
}
