import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Firebase {
	private const val lib = "com.google.firebase:firebase"
	private const val versionAnalyticsKtx = "20.0.2"
	private const val versionAuth = "21.0.1"
	private const val versionBom = "29.0.4"

	object Lib {
		const val analyticsKtx = "$lib-analytics-ktx:$versionAnalyticsKtx"
		const val auth = "$lib-auth:$versionAuth"
		const val bom = "$lib-bom:$versionBom"
	}
}

fun Project.implementFirebase() {
	dependencies {
		implement(Firebase.Lib.auth)
		implement(Firebase.Lib.bom)
	}
}
