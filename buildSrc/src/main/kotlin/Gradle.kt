import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object GradleVersion {
	const val kotlinGradlePluginVersion = "1.5.21"
	const val gradleVersion = "4.2.1"
}

object GradleLib {
	const val buildGradle = "com.android.tools.build:gradle:${GradleVersion.gradleVersion}"
	const val kotlinGradlePlugin =
		"org.jetbrains.kotlin:kotlin-gradle-plugin:${GradleVersion.kotlinGradlePluginVersion}"
}

fun Project.implementGradle() {
	dependencies {
		add("implementation", GradleLib.buildGradle)
		add("implementation", GradleLib.kotlinGradlePlugin)
	}
}

fun ScriptHandlerScope.gradleClasspath() {
	dependencies {
		add("classpath", GradleLib.buildGradle)
		add("classpath", GradleLib.kotlinGradlePlugin)
	}
}