import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object GoogleVersion {
	const val googleServicesVersion = "4.3.8"
	const val playServicesAuthVersion = "19.2.0"
	const val googleClient = "1.23.0"
	const val googleApiServicesDrive = "v3-rev110-1.23.0"
}

object GoogleLib {
	const val googleServices =
		"com.google.gms:google-services:${GoogleVersion.googleServicesVersion}"
	const val playServicesAuth =
		"com.google.android.gms:play-services-auth:${GoogleVersion.playServicesAuthVersion}"
	const val googleApiClient =
		"com.google.api-client:google-api-client:${GoogleVersion.googleClient}"
	const val googleOauthClientJetty =
		"com.google.oauth-client:google-oauth-client-jetty:${GoogleVersion.googleClient}"
	const val googleApiServicesDrive =
		"com.google.apis:google-api-services-drive:${GoogleVersion.googleApiServicesDrive}"

}

fun Project.implementGoogle() {
	dependencies {
		add("implementation", GoogleLib.playServicesAuth)
		add("implementation", GoogleLib.googleApiClient)
		add("implementation", GoogleLib.googleOauthClientJetty)
		add("implementation", GoogleLib.googleApiServicesDrive)
	}
}

fun ScriptHandlerScope.googleClasspath() {
	dependencies {
		add("classpath", GoogleLib.googleServices)
	}
}