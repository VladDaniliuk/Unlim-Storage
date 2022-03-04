plugins {
	id(Plugin.library)
	id(Plugin.kotlinAndroid)
	id(Plugin.kotlinKapt)
}

android {
	namespace = LocalStorage.namespace
	compileSdk = AndroidVersion.compileSdkVersion

	defaultConfig {
		minSdk = AndroidVersion.minSdkVersion
		targetSdk = AndroidVersion.compileSdkVersion

		testInstrumentationRunner = Config.testInstrumentationRunner
	}

	buildTypes {
		release {
			isMinifyEnabled = true
		}
		debug {
			isMinifyEnabled = true
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	implementForLocalStorage()
}
