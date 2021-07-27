plugins {
	id(Plugin.application)
	id(Plugin.hilt)
	id(Plugin.kotlinAndroid)
	id(Plugin.googleServices)
	id(Plugin.kotlinKapt)
}

android {
	buildFeatures {
		compose = true
	}

	buildTypes {
		getByName(BuildType.release) {
			signingConfig = signingConfigs.getByName(BuildType.debug)
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)
		}

		getByName(BuildType.debug) {
			signingConfig = signingConfigs.getByName(BuildType.debug)
			isDebuggable = true
		}
	}

	defaultConfig {
		applicationId = Config.applicationId
		minSdk = AndroidVersion.minSdkVersion
		targetSdk = AndroidVersion.compileSdkVersion
		versionCode = Application.versionCode
		versionName = Application.versionName
		testInstrumentationRunner = Config.testInstrumentationRunner
	}

	setCompileSdkVersion(AndroidVersion.compileSdkVersion)

	signingConfigs {
		getByName(BuildType.debug) {
			storeFile = file(BuildProperties.STORE_FILE)
			keyAlias = BuildProperties.KEY_ALIAS
			keyPassword = BuildProperties.STORE_PASSWORD
			storePassword = BuildProperties.KEY_PASSWORD
		}
	}

	implementCore()
	implementDesign()
	implementFirebase()
	implementCoroutines()
	implementNetwork()
	implementDependencyInjection()
	implementTest()
}