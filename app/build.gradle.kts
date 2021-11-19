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

			applicationVariants.all {
				outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
					.forEach { output ->
						output.outputFileName = BuildType.outputName
					}
			}
		}

		getByName(BuildType.debug) {
			signingConfig = signingConfigs.getByName(BuildType.debug)
			isDebuggable = true

			applicationVariants.all {
				outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
					.forEach { output ->
						output.outputFileName = BuildType.outputNameDebug
					}
			}
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
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
	}

	setCompileSdkVersion(AndroidVersion.compileSdkVersion)

	implementKotlinx()
	implementCore()
	implementDesign()
	implementFirebase()
	implementGoogle()
	implementNetwork()
	implementTest()
	implementLifecycle()
	implementKotlin()
	implementNavigation()
	implementActivity()
	implementCompose()
	implementHilt()
	implementDropbox()
	implementBoxApi()
	implementLocalDataBase()
	implementCoil()
	implementAccompanist()
}
