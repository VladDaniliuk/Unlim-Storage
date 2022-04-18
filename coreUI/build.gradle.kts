plugins {
	id(Plugin.library)
	id(Plugin.kotlinAndroid)
	id(Plugin.kotlinKapt)
}

android {
	namespace = CoreUI.namespace
	compileSdk = AndroidVersion.compileSdkVersion

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

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += BuildType.requiresOptIn
	}

	implementForCoreUi()
}
