plugins {
	id(Plugin.application)
	id(Plugin.hilt)
	id(Plugin.kotlinAndroid)
	id(Plugin.googleServices)
	id(Plugin.kotlinKapt)
}

android {
	compileSdkVersion(AndroidVersion.compileSdkVersion)
	buildToolsVersion(AndroidVersion.buildToolsVersion)

	defaultConfig {
		applicationId(Config.applicationId)
		minSdkVersion(AndroidVersion.minSdkVersion)
		targetSdkVersion(AndroidVersion.compileSdkVersion)
		versionCode(Application.versionCode)
		versionName(Application.versionName)
		testInstrumentationRunner(Config.testInstrumentationRunner)
	}

	buildTypes {
		getByName(BuildType.release) {
			minifyEnabled(false)
			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)
		}
	}

	buildFeatures {
		dataBinding = true
	}

	implementCore()
	implementDesign()
	implementFirebase()
	implementCoroutines()
	implementNetwork()
	implementDependencyInjection()
	implementTest()
}