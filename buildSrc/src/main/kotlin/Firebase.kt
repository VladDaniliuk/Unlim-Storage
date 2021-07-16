import org.gradle.kotlin.dsl.DependencyHandlerScope

object FirebaseVersion {
	const val firebaseAnalyticsKtxVersion = "19.0.0"
	const val firebaseAuthVersion = "21.0.1"
	const val firebaseBomVersion = "28.2.1"
	const val googleServicesVersion = "4.3.8"
}

object FirebaseLib {
	const val firebaseAnalyticsKtx = "com.google.firebase:firebase-analytics-ktx:${
		FirebaseVersion.firebaseAnalyticsKtxVersion
	}"
	const val firebaseAuth =
		"com.google.firebase:firebase-auth:${FirebaseVersion.firebaseAuthVersion}"
	const val firebaseBom =
		"com.google.firebase:firebase-bom:${FirebaseVersion.firebaseBomVersion}"
	const val googleServices =
		"com.google.gms:google-services:${FirebaseVersion.googleServicesVersion}"
}

object FirebaseUsage {
	fun DependencyHandlerScope.implementFirebase() {
		add("implementation", FirebaseLib.firebaseAnalyticsKtx)
		add("implementation", FirebaseLib.firebaseAuth)
		add("implementation", FirebaseLib.firebaseBom)
		add("implementation", FirebaseLib.googleServices)
	}

	fun implementFirebaseClasspath(it: DependencyHandlerScope) {
		it {
			add("classpath", FirebaseLib.googleServices)
		}
	}

	/*fun DependencyHandlerScope.implementFirebaseClasspath() {
		add("classpath", FirebaseLib.googleServices)
	}*/
}