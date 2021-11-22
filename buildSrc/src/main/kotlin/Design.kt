import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object DesignVersion {
	const val appcompatVersion = "1.3.1"
	const val constraintlayoutVersion = "2.1.1"
	const val materialVersion = "1.4.0"
}

object DesignLib {
	const val appcompat = "androidx.appcompat:appcompat:${DesignVersion.appcompatVersion}"
	const val constraintlayout =
		"androidx.constraintlayout:constraintlayout:${DesignVersion.constraintlayoutVersion}"
	const val material = "com.google.android.material:material:${DesignVersion.materialVersion}"
}

fun Project.implementDesign() {
	dependencies {
		add("implementation", DesignLib.appcompat)
	}
}
