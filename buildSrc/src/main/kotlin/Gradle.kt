import org.gradle.kotlin.dsl.ScriptHandlerScope

object GradleVersion {
	const val kotlinGradlePluginVersion = "1.5.21"
	const val gradleVersion = "4.2.2"
}

object GradleLib {
	const val buildGradle = "com.android.tools.build:gradle:${GradleVersion.gradleVersion}"
	const val kotlinGradlePlugin =
		"org.jetbrains.kotlin:kotlin-gradle-plugin:${GradleVersion.kotlinGradlePluginVersion}"
}

fun ScriptHandlerScope.gradleClasspath() {
	dependencies {
		add("classpath", GradleLib.buildGradle)
		add("classpath", GradleLib.kotlinGradlePlugin)
	}
}