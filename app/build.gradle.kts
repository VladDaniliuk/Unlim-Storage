plugins {
	id(Plugin.application)
	id(Plugin.hilt)
	id(Plugin.kotlinAndroid)
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

			isMinifyEnabled = true
			isShrinkResources = true

			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)

			versionNameSuffix = Application.releaseSuffix
		}

		getByName(BuildType.debug) {
			signingConfig = signingConfigs.getByName(BuildType.debug)
			isDebuggable = true

			isMinifyEnabled = true
			isShrinkResources = true

			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)

			versionNameSuffix = Application.debugSuffix
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
		kotlinCompilerExtensionVersion = Compose.version
	}

	defaultConfig {
		applicationId = Config.applicationId
		minSdk = AndroidVersion.minSdkVersion
		targetSdk = AndroidVersion.compileSdkVersion
		versionCode = Application.versionCode
		versionName = Application.versionName
		testInstrumentationRunner = Config.testInstrumentationRunner
		base.archivesBaseName = Application.name
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += BuildType.requiresOptIn
		kotlinOptions.freeCompilerArgs += BuildType.xjvmDefault
	}

	packagingOptions.resources.excludes.customExclude()

	setCompileSdkVersion(AndroidVersion.compileSdkVersion)

	implementAll()
}
