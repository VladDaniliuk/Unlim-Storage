import org.gradle.kotlin.dsl.DependencyHandlerScope

object DesignVersion {
	const val appcompatVersion = "1.3.0"
	const val constraintlayoutVersion = "2.0.4"
	const val materialVersion = "1.4.0"
}

object DesignLib {
	const val appcompat = "androidx.appcompat:appcompat:${DesignVersion.appcompatVersion}"
	const val constraintlayout =
		"androidx.constraintlayout:constraintlayout:${DesignVersion.constraintlayoutVersion}"
	const val material = "com.google.android.material:material:${DesignVersion.materialVersion}"
}

object DesignUsage {
	fun DependencyHandlerScope.implementDesign() {
		add("implementation", DesignLib.appcompat)
		add("implementation", DesignLib.constraintlayout)
		add("implementation", DesignLib.material)
	}
}