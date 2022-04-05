import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies

object Google {
	private const val lib = "com.google"
	private const val versionServices = "4.3.10"
	private const val versionAuth = "20.1.0"
	private const val versionClient = "1.33.4"
	private const val versionDrive = "v3-rev197-1.25.0"
	private const val versionGson = "2.8.9"

	object Lib {
		const val services = "$lib.gms:google-services:$versionServices"
		const val auth = "$lib.android.gms:play-services-auth:$versionAuth"
		const val clientAndroid = "$lib.api-client:google-api-client-android:$versionClient"
		const val clientJetty = "$lib.oauth-client:google-oauth-client-jetty:$versionClient"
		const val drive = "$lib.apis:google-api-services-drive:$versionDrive"
		const val gson = "$lib.code.gson:gson:$versionGson"

		//For resolving problem with duplicates
		const val listenablefuture =
			"com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"
	}
}

fun Project.implementGoogleForGoogleStorage() {
	dependencies {
		implement(Google.Lib.drive)
		implement(Google.Lib.auth)
		implement(Google.Lib.clientAndroid)
	}
}

fun ScriptHandlerScope.googleClasspath() {
	dependencies {
		classpath(Google.Lib.services)
	}
}
