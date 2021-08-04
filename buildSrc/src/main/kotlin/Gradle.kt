import org.gradle.kotlin.dsl.ScriptHandlerScope

object GradleVersion {
	const val kotlinGradlePluginVersion =
		"1.5.10"//"1.5.30-M1"\\ уменьшение для jetpack compose 1.0.0-rc02
	const val gradleVersion = "7.0.0-rc01"
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
