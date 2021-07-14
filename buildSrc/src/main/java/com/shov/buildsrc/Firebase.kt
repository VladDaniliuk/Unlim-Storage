package com.shov.buildsrc

object FirebaseVersion {
	const val firebaseAuthVersion = "21.0.1"
	const val googleServicesVersion = "4.3.8"
	const val firebaseBomVersion = "28.2.1"
	const val firebaseAnalyticsKtxVersion = "19.0.0"
}

object FirebaseLib {
	const val googleServices =
		"com.google.gms:google-services:${FirebaseVersion.googleServicesVersion}"
	const val firebaseAuth =
		"com.google.firebase:firebase-auth:${FirebaseVersion.firebaseAuthVersion}"
	const val firebaseBom = "com.google.firebase:firebase-bom:${FirebaseVersion.firebaseBomVersion}"
	const val firebaseAnalyticsKtx =
		"com.google.firebase:firebase-analytics-ktx:${FirebaseVersion.firebaseAnalyticsKtxVersion}"
}