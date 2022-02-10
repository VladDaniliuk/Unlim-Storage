import org.gradle.kotlin.dsl.ScriptHandlerScope

object Gradle {
	private const val lib = "com.android.tools.build:gradle:"
	private const val version = "7.1.1"

	object Lib {
		const val gradle = "$lib$version"
	}
}

fun ScriptHandlerScope.gradleClasspath() {
	dependencies {
		classpath(Gradle.Lib.gradle)
	}
}
