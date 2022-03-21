plugins {
	id(Plugin.library)
	id(Plugin.kotlinAndroid)
}

android {
	namespace = CoreUtils.namespace
	compileSdk = 32

	buildFeatures {
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion = Compose.version
	}

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

	implementForCoreUtils()
}
