import org.gradle.kotlin.dsl.DependencyHandlerScope

object DependencyInjectionVersion {
	const val databindingVersion = "3.1.4"
	const val hiltAndroidVersion = "2.37"
	const val navigationVersion = "2.3.5"
}

object DependencyInjectionLib {
	const val databinding =
		"com.android.databinding:compiler:${DependencyInjectionVersion.databindingVersion}"
	const val hiltAndroid =
		"com.google.dagger:hilt-android:${DependencyInjectionVersion.hiltAndroidVersion}"
	const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${
		DependencyInjectionVersion.hiltAndroidVersion
	}"
	const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${
		DependencyInjectionVersion.hiltAndroidVersion
	}"
	const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${
		DependencyInjectionVersion.navigationVersion
	}"
	const val navigationUiKtx =
		"androidx.navigation:navigation-ui-ktx:${DependencyInjectionVersion.navigationVersion}"
}

object DependencyInjectionUsage {
	fun DependencyHandlerScope.implementDependencyInjection() {
		add("kapt", DependencyInjectionLib.databinding)
		add("implementation", DependencyInjectionLib.hiltAndroid)
		add("kapt", DependencyInjectionLib.hiltAndroidCompiler)
		add("implementation", DependencyInjectionLib.hiltAndroidGradlePlugin)
		add("implementation", DependencyInjectionLib.navigationFragmentKtx)
		add("implementation", DependencyInjectionLib.navigationUiKtx)
	}

	fun implementDependencyInjectionClasspath(it: DependencyHandlerScope) {
		it {
			add("classpath", DependencyInjectionLib.hiltAndroidGradlePlugin)
		}
	}
}