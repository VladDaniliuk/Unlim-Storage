object AndroidVersion {
	const val compileSdkVersion = 31
	const val buildToolsVersion = "30.0.3"
	const val minSdkVersion = 25
	const val versionCode = 1
	const val versionName = "1.0"
}

object Application {
	const val name = "Unlim Storage"
	const val versionCode = 1
	const val versionName = "1.0"
}

object Config {
	const val applicationId = "com.shov.unlimstorage"
	const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildType {
	const val release = "release"
	const val debug = "debug"
	const val proguardFile = "proguard-android-optimize.txt"
	const val proguardRules = "proguard-rules.pro"
}

object KotlinOptions {
	const val jvmTarget = "1.8"
}
