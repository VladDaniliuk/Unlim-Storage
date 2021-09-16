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

	buildToolsVersion = AndroidVersion.buildToolsVersion

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

	signingConfigs {
		getByName(BuildType.debug) {
			storeFile = file(BuildProperties.STORE_FILE)
			keyAlias = BuildProperties.KEY_ALIAS
			keyPassword = BuildProperties.STORE_PASSWORD
			storePassword = BuildProperties.KEY_PASSWORD
		}
	}

	composeOptions {
		kotlinCompilerExtensionVersion = ComposeVersion.composeVersion
	}

	defaultConfig {
		applicationId = Config.applicationId
		minSdk = AndroidVersion.minSdkVersion
		targetSdk = AndroidVersion.compileSdkVersion
		versionCode = Application.versionCode
		versionName = Application.versionName
		testInstrumentationRunner = Config.testInstrumentationRunner
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	setCompileSdkVersion(AndroidVersion.compileSdkVersion)

	implementCore()
	implementDesign()
	implementFirebase()
	implementGoogle()
	implementCoroutines()
	implementNetwork()
	implementDependencyInjection()
	implementTest()
	implementCompose()
	implementDropBox()
	implementBoxApi()
	implementLocalDataBase()
}
