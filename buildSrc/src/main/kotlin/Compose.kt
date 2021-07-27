import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object ComposeVersion {
	const val activityComposeVersion = "1.3.0-rc02"
	const val composeVersion = "1.0.0-rc02"
	const val kotlinStdlibVersion = "1.5.21"
	const val runtimeLivedataVersion = "1.0.0-rc02"
}

object ComposeLib {
	const val activityCompose =
		"androidx.activity:activity-compose:${ComposeVersion.activityComposeVersion}"
	const val composeMaterial =
		"androidx.compose.material:material:${ComposeVersion.composeVersion}"
	const val composeUi = "androidx.compose.ui:ui:${ComposeVersion.composeVersion}"
	const val composeUiTooling = "androidx.compose.ui:ui-tooling:${ComposeVersion.composeVersion}"
	const val kotlinStdlib =
		"org.jetbrains.kotlin:kotlin-stdlib:${ComposeVersion.kotlinStdlibVersion}"
	const val runtimeLivedata =
		"androidx.compose.runtime:runtime-livedata:${ComposeVersion.runtimeLivedataVersion}"
}

fun Project.implementCompose() {
	dependencies {
		add("implementation", ComposeLib.kotlinStdlib)
		add("implementation", ComposeLib.composeUi)
		add("implementation", ComposeLib.activityCompose)
		add("implementation", ComposeLib.composeMaterial)
		add("implementation", ComposeLib.composeUiTooling)
		add("implementation", ComposeLib.runtimeLivedata)
	}
}