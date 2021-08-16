import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object BoxVersion {
	const val boxAndroidSdkVersion = "5.0.0"
}

object BoxLib {
	const val boxAndroidSdk = "com.box:box-android-sdk:${BoxVersion.boxAndroidSdkVersion}"
}

fun Project.implementBoxApi() {
	dependencies {
		add("implementation", BoxLib.boxAndroidSdk)
	}
}
