import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object Kotlin {
	private const val lib = "org.jetbrains.kotlin:kotlin"
	private const val version = "1.6.0-RC2"
	private const val pluginVersion = "1.5.31"//for compose 1.0.4

	object Lib {
		const val stdLib = "$lib-stdlib:$version"
		const val gradlePlugin = "$lib-gradle-plugin:$pluginVersion"
	}
}

fun Project.implementKotlin() {
	dependencies {
		implement(Kotlin.Lib.stdLib)
	}
}

fun ScriptHandlerScope.kotlinClasspath() {
	dependencies {
		classpath(Kotlin.Lib.gradlePlugin)
	}
}