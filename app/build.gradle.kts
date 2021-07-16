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
		//CoreUsage.implementCore()
		this.implementCore()
		//implementation(CoreLib.coreKtx)
		//DesignUsage.implementDesign()
		this.implementDesign()
		/*implementation(DesignLib.appcompat)
		implementation(DesignLib.material)
		implementation(DesignLib.constraintlayout)*/
		//FirebaseUsage.implementFirebase()
		this.implementFirebase()
		/*implementation(FirebaseLib.firebaseAuth)
		implementation(FirebaseLib.firebaseBom)
		implementation(FirebaseLib.firebaseAnalyticsKtx)*/
		//CoroutinesUsage.implementCoroutines()
		this.implementCoroutines()
		/*implementation(CoroutinesLib.kotlinxCoroutinesCore)
		implementation(CoroutinesLib.kotlinxCoroutinesAndroid)*/
		//NetworkUsage.implementNetwork()
		this.implementNetwork()
		/*implementation(NetworkLib.moshiKotlin)
		kapt(NetworkLib.moshiKotlinCodegen)
		implementation(NetworkLib.retrofit)
		implementation(NetworkLib.converterMoshi)
		implementation(NetworkLib.okhttp)
		implementation(NetworkLib.loggingInterceptor)*/
		//DependencyInjectionUsage.implementDependencyInjection()
		this.implementDependencyInjection()
		/*implementation(DependencyInjectionLib.hiltAndroid)
		kapt(DependencyInjectionLib.hiltAndroidCompiler)
		implementation(DependencyInjectionLib.navigationFragmentKtx)
		implementation(DependencyInjectionLib.navigationUiKtx)
		kapt(DependencyInjectionLib.databinding)*/
		//TestUsage.implementTest()
		this.implementTest()
		/*testImplementation(TestLib.junit)
		androidTestImplementation(TestLib.extJunit)
		androidTestImplementation(TestLib.espresso)*/
	}
}