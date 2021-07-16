buildscript {
	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		GradleUsage.implementGradleClasspath(this)
		DependencyInjectionUsage.implementDependencyInjectionClasspath(this)
		FirebaseUsage.implementFirebaseClasspath(this)
	}

/*
* com.shov.buildsrc.GradleUsage.implementGradleClasspath1(this)
* com.shov.buildsrc.FirebaseUsage.implementFirebaseClasspath1(this)
* com.shov.buildsrc.DependencyInjectionUsage.implementDependencyInjectionClasspath1(this)
*/

/*
* classpath(com.shov.buildsrc.GradleLib.buildGradle)
* classpath(com.shov.buildsrc.GradleLib.kotlinGradlePlugin)
* classpath(com.shov.buildsrc.FirebaseLib.googleServices)
* classpath(com.shov.buildsrc.DependencyInjectionLib.hiltAndroidGradlePlugin)
*/

/*
* classpath("com.android.tools.build:gradle:4.2.1")
* classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
* classpath("com.google.gms:google-services:4.3.8")
* classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")
*/
}