package com.shov.buildsrc

object DependencyVersion {
	const val databindingVersion = "3.1.4"
	const val navigationVersion = "2.3.5"
	const val hiltAndroidVersion = "2.37"
}

object DependencyLib {
	const val hiltAndroid = "com.google.dagger:hilt-android:${DependencyVersion.hiltAndroidVersion}"
	const val hiltAndroidCompiler =
		"com.google.dagger:hilt-android-compiler:${DependencyVersion.hiltAndroidVersion}"
	const val navigationFragmentKtx =
		"androidx.navigation:navigation-fragment-ktx:${DependencyVersion.navigationVersion}"
	const val navigationUiKtx =
		"androidx.navigation:navigation-ui-ktx:${DependencyVersion.navigationVersion}"
	const val databinding =
		"com.android.databinding:compiler:${DependencyVersion.databindingVersion}"
	const val hiltAndroidGradlePlugin =
		"com.google.dagger:hilt-android-gradle-plugin:${DependencyVersion.hiltAndroidVersion}"

}