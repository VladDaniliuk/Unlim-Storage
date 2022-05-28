object AndroidVersion {
	const val compileSdkVersion = 31
	const val buildToolsVersion = "30.0.3"
	const val minSdkVersion = 25
}

object Application {
	const val name = "Unlim Storage"
	const val versionCode = 6
	const val versionName = "0.2.7.4"
	const val debugSuffix = "-debug"
	const val releaseSuffix = "-release"
}

object Config {
	const val applicationId = "com.shov.unlimstorage"
	const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildType {
	const val buildDataProperties = "build-data.properties"
	const val debug = "debug"
	const val proguardFile = "proguard-android-optimize.txt"
	const val proguardRules = "proguard-rules.pro"
	const val release = "release"
	const val requiresOptIn = "-Xopt-in=kotlin.RequiresOptIn"
	const val xjvmDefault = "-Xjvm-default=compatibility"
	const val metaInfDependencies = "META-INF/DEPENDENCIES"
}

object KotlinOptions {
	const val jvmTarget = "11"
}
