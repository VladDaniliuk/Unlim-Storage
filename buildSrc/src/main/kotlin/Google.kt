import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Google {
	private const val lib = "com.google"
	private const val versionServices = "4.3.10"
	private const val versionClient = "1.33.2"
	private const val versionDrive = "v3-rev197-1.25.0"
	private const val versionGson = "2.8.9"

	object Android {
		private const val subLib = "$lib.android"
		private const val versionComposeThemeAdapter = "1.1.3"
		private const val versionAuth = "20.0.1"

		object Lib {
			const val auth = "$subLib.gms:play-services-auth:$versionAuth"
			const val composeThemeAdapter =
				"$subLib.material:compose-theme-adapter:$versionComposeThemeAdapter"
		}
	}

	object Lib {
		const val clientAndroid = "$lib.api-client:google-api-client-android:$versionClient"
		const val clientJetty = "$lib.oauth-client:google-oauth-client-jetty:$versionClient"
		const val drive = "$lib.apis:google-api-services-drive:$versionDrive"
		const val gson = "$lib.code.gson:gson:$versionGson"
		const val services = "$lib.gms:google-services:$versionServices"
	}
}

fun Project.implementGoogle() {
	dependencies {
		implement(Google.Android.Lib.composeThemeAdapter)
		implement(Google.Android.Lib.auth)
		implement(Google.Lib.clientAndroid)
		implement(Google.Lib.drive)
		implement(Google.Lib.gson)
	}
}
