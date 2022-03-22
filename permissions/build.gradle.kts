plugins {
	id(Plugin.library)
	id(Plugin.kotlinAndroid)
}

android {
	buildFeatures {
		compose = true
	}

	namespace = Permissions.namespace
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

	composeOptions {
		kotlinCompilerExtensionVersion = Compose.version
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	implementForPermissions()
}
