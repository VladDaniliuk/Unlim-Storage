plugins {
	addAll()
}

android {
	buildFeatures {
		compose = true
	}

	buildToolsVersion = AndroidVersion.buildToolsVersion

	signingConfigs {
		debug {
			storeFile = file(BuildProperties.STORE_FILE)
			keyAlias = BuildProperties.KEY_ALIAS
			keyPassword = BuildProperties.STORE_PASSWORD
			storePassword = BuildProperties.KEY_PASSWORD
		}

		release {
			storeFile = file(BuildProperties.STORE_FILE)
			keyAlias = BuildProperties.KEY_ALIAS
			keyPassword = BuildProperties.STORE_PASSWORD
			storePassword = BuildProperties.KEY_PASSWORD
		}
	}

	buildTypes {
		debug {
			signingConfig = signingConfigs.debug
			isDebuggable = true

			isMinifyEnabled = true
			isShrinkResources = true

			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)

			applicationVariants.all {
				outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
					.forEach { output ->
						output.outputFileName = BuildType.outputNameDebug
					}
			}
		}

		release {
			signingConfig = signingConfigs.release

			isMinifyEnabled = true
			isShrinkResources = true

			proguardFiles(getDefaultProguardFile(BuildType.proguardFile), BuildType.proguardRules)

			applicationVariants.all {
				outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
					.forEach { output ->
						output.outputFileName = BuildType.outputName
					}
			}
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

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = KotlinOptions.jvmTarget
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += BuildType.requiresOptIn
		kotlinOptions.freeCompilerArgs += BuildType.xjvmDefault
	}

	packagingOptions.resources {
		excludes.add(BuildType.buildDataProperties)
		excludes.add(BuildType.metaInfDependencies)
	}

	setCompileSdkVersion(AndroidVersion.compileSdkVersion)

	implementAll()
}
