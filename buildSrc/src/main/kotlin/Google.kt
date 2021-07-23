import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object GoogleVersion {
	const val googleServicesVersion = "4.3.8"
	const val playServicesAuthVersion = "19.2.0"
}

object GoogleLib {
	const val googleServices =
		"com.google.gms:google-services:${GoogleVersion.googleServicesVersion}"
	const val playServicesAuth =
		"com.google.android.gms:play-services-auth:${GoogleVersion.playServicesAuthVersion}"
}

fun Project.implementGoogle() {
	dependencies {
		add("implementation", GoogleLib.googleServices)
		add("implementation", GoogleLib.playServicesAuth)
	}
}

fun ScriptHandlerScope.googleClasspath() {
	dependencies {
		add("classpath", GoogleLib.googleServices)
	}
}