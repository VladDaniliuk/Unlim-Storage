plugins {
	id(Plugin.application)
	id(Plugin.hilt)
	id(Plugin.kotlinAndroid)
	id(Plugin.googleServices)
	id(Plugin.kotlinKapt)
}

android {
	compileSdkVersion(31)
	buildToolsVersion = "30.0.3"

	defaultConfig {
		applicationId = "com.shov.unlimstorage"
		minSdkVersion(27)
		targetSdkVersion(31)
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro")
		}
	}

	buildFeatures {
		dataBinding = true
	}

	implementGradle()
	implementCore()
	implementDesign()
	implementFirebase()
	implementCoroutines()
	implementNetwork()
	implementDependencyInjection()
	implementTest()
}