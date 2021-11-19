import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object Google {
	private const val lib = "com.google"
	private const val versionServices = "4.3.10"
	private const val versionAuth = "19.2.0"
	private const val versionClient = "1.23.0"
	private const val versionDrive = "v3-rev110-1.23.0"

	object Lib {
		const val services = "$lib.gms:google-services:$versionServices"
		const val auth = "$lib.android.gms:play-services-auth:$versionAuth"
		const val clientAndroid = "$lib.api-client:google-api-client-android:$versionClient"
		const val clientJetty = "$lib.oauth-client:google-oauth-client-jetty:$versionClient"
		const val drive = "$lib.apis:google-api-services-drive:$versionDrive"
	}
}

fun Project.implementGoogle() {
	dependencies {
		implement(Google.Lib.auth)
		implement(Google.Lib.clientAndroid)
		implement(Google.Lib.clientJetty)
		implement(Google.Lib.drive)
	}
}

fun ScriptHandlerScope.googleClasspath() {
	dependencies {
		classpath(Google.Lib.services)
	}
}
