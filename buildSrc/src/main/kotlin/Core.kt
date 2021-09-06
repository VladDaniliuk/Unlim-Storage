import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object CoreVersion {
	const val coreKtxVersion = "1.6.0"
	const val coreSplashScreenVersion = "1.0.0-alpha01"
}

object CoreLib {
	const val coreKtx = "androidx.core:core-ktx:${CoreVersion.coreKtxVersion}"
	const val coreSplashScreen =
		"androidx.core:core-splashscreen:${CoreVersion.coreSplashScreenVersion}"
}

fun Project.implementCore() {
	dependencies {
		add("implementation", CoreLib.coreKtx)
		add("implementation", CoreLib.coreSplashScreen)
	}
}
