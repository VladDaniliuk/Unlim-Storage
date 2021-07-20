import CoreUsage.implementCore
import CoroutinesUsage.implementCoroutines
import DependencyInjectionUsage.implementDependencyInjection
import DesignUsage.implementDesign
import FirebaseUsage.implementFirebase
import NetworkUsage.implementNetwork
import TestUsage.implementTest

plugins {
	id("com.android.application")
	id("dagger.hilt.android.plugin")
	id("kotlin-android")
	id("com.google.gms.google-services")
	id("kotlin-kapt")
}

android {
	compileSdkVersion(30)
	buildToolsVersion = "30.0.3"

	defaultConfig {
		applicationId = "com.shov.unlimstorage"
		minSdkVersion(27)
		targetSdkVersion(30)
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}

	buildFeatures {
		dataBinding = true
	}

	dependencies {
		this.implementCore()
		this.implementDesign()
		this.implementFirebase()
		this.implementCoroutines()
		this.implementNetwork()
		this.implementDependencyInjection()
		this.implementTest()
	}
}