import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object ComposeVersion {
	const val activityComposeVersion = "1.3.1"
	const val composeVersion = "1.0.4"
	const val kotlinStdlibVersion = "1.5.31"
	const val runtimeLivedataVersion = "1.0.0"
	const val navigationComposeVersion = "2.4.0-alpha10"
	const val hiltNavigationComposeVersion = "1.0.0-alpha03"
	const val composeMaterialIconsVersion = "1.1.0-alpha06"
	const val accompanistSwiperefreshVersion = "0.19.0"
	const val composeMaterial3Version = "1.0.0-alpha01" //TODO migrate to material3
}                                                       //TODO color scheme of material3

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
	const val navigationCompose =
		"androidx.navigation:navigation-compose:${ComposeVersion.navigationComposeVersion}"
	const val hiltNavigationCompose =
		"androidx.hilt:hilt-navigation-compose:${ComposeVersion.hiltNavigationComposeVersion}"
	const val composeMaterialIcons = "androidx.compose.material:material-icons-extended:${
		ComposeVersion.composeMaterialIconsVersion
	}"
	const val accompanistSwiperefresh = "com.google.accompanist:accompanist-swiperefresh:${
		ComposeVersion.accompanistSwiperefreshVersion
	}"
	const val composeMaterial3 =
		"androidx.compose.material3:material3:${ComposeVersion.composeMaterial3Version}"
}

fun Project.implementCompose() {
	dependencies {
		add("implementation", ComposeLib.kotlinStdlib)
		add("implementation", ComposeLib.composeUi)
		add("implementation", ComposeLib.activityCompose)
		add("implementation", ComposeLib.composeMaterial)
		add("implementation", ComposeLib.composeUiTooling)
		add("implementation", ComposeLib.navigationCompose)
		add("implementation", ComposeLib.hiltNavigationCompose)
		add("implementation", ComposeLib.composeMaterialIcons)
		add("implementation", ComposeLib.accompanistSwiperefresh)
	}
}
