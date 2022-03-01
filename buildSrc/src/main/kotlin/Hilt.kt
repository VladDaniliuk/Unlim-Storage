import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object Hilt {
	private const val hiltLib = "androidx.hilt:hilt"
	private const val daggerLib = "com.google.dagger:hilt-android"
	private const val hiltVersion = "1.0.0"
	private const val daggerVersion = "2.41"

	object Lib {
		const val compiler = "$daggerLib-compiler:$daggerVersion"
		const val gradlePlugin = "$daggerLib-gradle-plugin:$daggerVersion"
		const val hilt = "$daggerLib:$daggerVersion"
		const val navigationCompose = "$hiltLib-navigation-compose:$hiltVersion"
		const val work = "$hiltLib-work:$hiltVersion"
	}
}

fun Project.implementHilt() {
	dependencies {
		implement(Hilt.Lib.navigationCompose)
		implement(Hilt.Lib.hilt)
		kapt(Hilt.Lib.compiler)
		implement(Hilt.Lib.gradlePlugin)
	}
}

fun ScriptHandlerScope.hiltClasspath() {
	dependencies {
		classpath(Hilt.Lib.gradlePlugin)
	}
}

fun Project.implementHiltForModules() {
	dependencies {
		implement(Hilt.Lib.hilt)
	}
}
