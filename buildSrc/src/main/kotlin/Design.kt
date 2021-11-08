import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object DesignVersion {
	const val appcompatVersion = "1.3.1"
	const val constraintlayoutVersion = "2.1.1"
	const val materialVersion = "1.4.0"
	const val composeSettingsUiVersion = "0.7.2"
}

object DesignLib {
	const val appcompat = "androidx.appcompat:appcompat:${DesignVersion.appcompatVersion}"
	const val constraintlayout =
		"androidx.constraintlayout:constraintlayout:${DesignVersion.constraintlayoutVersion}"
	const val material = "com.google.android.material:material:${DesignVersion.materialVersion}"
	//lib provides a set of Settings like composable items
	const val composeSettingsUi =
		"com.github.alorma:compose-settings-ui:${DesignVersion.composeSettingsUiVersion}"
}

fun Project.implementDesign() {
	dependencies {
		add("implementation", DesignLib.appcompat)
		add("implementation", DesignLib.constraintlayout)
		add("implementation", DesignLib.material)
		add("implementation", DesignLib.composeSettingsUi)
	}
}
