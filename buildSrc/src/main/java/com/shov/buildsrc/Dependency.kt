package com.shov.buildsrc

object PluginVersion {
	const val coreKtxVersion = "1.6.0"
	const val appcompatVersion = "1.3.0"
	const val materialVersion = "1.4.0"
	const val constraintlayoutVersion = "2.0.4"
	const val firebaseAuthVersion = "21.0.1"
	const val firebaseBomVersion = "28.2.1"
	const val firebaseAnalyticsKtxVersion = "19.0.0"
	const val kotlinxCoroutinesVersion = "1.5.1"
	const val moshiKotlinVersion = "1.12.0"
	const val retofitVersion = "2.9.0"
	const val okhttpVersion = "4.9.1"
	const val hiltAndroidVersion = "2.37"
	const val navigationVersion = "2.3.5"
	const val databindingVersion = "3.1.4"
	const val junitVersion = "4.13.2"
	const val extJunitVersion = "1.1.3"
	const val espressoVersion = "3.4.0"
}

object PluginName {
	const val coreKtx = "androidx.core:core-ktx:${PluginVersion.coreKtxVersion}"
	const val appcompat = "androidx.appcompat:appcompat:${PluginVersion.appcompatVersion}"
	const val material = "com.google.android.material:material:${PluginVersion.materialVersion}"
	const val constraintlayout =
		"androidx.constraintlayout:constraintlayout:${PluginVersion.constraintlayoutVersion}"
	const val firebaseAuth =
		"com.google.firebase:firebase-auth:${PluginVersion.firebaseAuthVersion}"
	const val firebaseBom = "com.google.firebase:firebase-bom:${PluginVersion.firebaseBomVersion}"
	const val firebaseAnalyticsKtx =
		"com.google.firebase:firebase-analytics-ktx:${PluginVersion.firebaseAnalyticsKtxVersion}"
	const val kotlinxCoroutinesCore =
		"org.jetbrains.kotlinx:kotlinx-coroutines-core:${PluginVersion.kotlinxCoroutinesVersion}"
	const val kotlinxCoroutinesAndroid =
		"org.jetbrains.kotlinx:kotlinx-coroutines-android:${PluginVersion.kotlinxCoroutinesVersion}"
	const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${PluginVersion.moshiKotlinVersion}"
	const val moshiKotlinCodegen =
		"com.squareup.moshi:moshi-kotlin-codegen:${PluginVersion.moshiKotlinVersion}"
	const val retrofit = "com.squareup.retrofit2:retrofit:${PluginVersion.retofitVersion}"
	const val converterMoshi =
		"com.squareup.retrofit2:converter-moshi:${PluginVersion.retofitVersion}"
	const val okhttp = "com.squareup.okhttp3:okhttp:${PluginVersion.okhttpVersion}"
	const val loggingInterceptor =
		"com.squareup.okhttp3:logging-interceptor:${PluginVersion.okhttpVersion}"
	const val hiltAndroid = "com.google.dagger:hilt-android:${PluginVersion.hiltAndroidVersion}"
	const val hiltAndroidCompiler =
		"com.google.dagger:hilt-android-compiler:${PluginVersion.hiltAndroidVersion}"
	const val navigationFragmentKtx =
		"androidx.navigation:navigation-fragment-ktx:${PluginVersion.navigationVersion}"
	const val navigationUiKtx =
		"androidx.navigation:navigation-ui-ktx:${PluginVersion.navigationVersion}"
	const val databinding = "com.android.databinding:compiler:${PluginVersion.databindingVersion}"
	const val junit = "junit:junit:${PluginVersion.junitVersion}"
	const val extJunit = "androidx.test.ext:junit:${PluginVersion.extJunitVersion}"
	const val esspresso = "androidx.test.espresso:espresso-core:${PluginVersion.espressoVersion}"
}