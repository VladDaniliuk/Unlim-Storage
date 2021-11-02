import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object CoreVersion {
	const val coreKtxVersion = "1.6.0"
	const val coreSplashScreenVersion = "1.0.0-alpha02"
	const val kotlinxDatetimeVersion = "0.3.1"
}

object CoreLib {
	const val coreKtx = "androidx.core:core-ktx:${CoreVersion.coreKtxVersion}"
	const val coreSplashScreen =
		"androidx.core:core-splashscreen:${CoreVersion.coreSplashScreenVersion}"
	const val kotlinxDatetime =
		"org.jetbrains.kotlinx:kotlinx-datetime:${CoreVersion.kotlinxDatetimeVersion}"
}

fun Project.implementCore() {
	dependencies {
		add("implementation", CoreLib.coreKtx)
		add("implementation", CoreLib.coreSplashScreen)
		add("implementation", CoreLib.kotlinxDatetime)
	}
}
