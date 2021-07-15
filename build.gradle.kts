buildscript {
	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		//this.implementGradleClasspath()

		//com.shov.buildsrc.Gradle.GradleUsage.implementGradleClasspath1(this)
		//implementation("com.android.tools.build:gradle:4.2.1")
		classpath(com.shov.buildsrc.Gradle.GradleLib.buildGradle)
		//classpath("com.android.tools.build:gradle:4.2.1")
		classpath(com.shov.buildsrc.Gradle.GradleLib.kotlinGradlePlugin)
		//classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
		//com.shov.buildsrc.Firebase.FirebaseUsage.implementFirebaseClasspath1(this)
		classpath(com.shov.buildsrc.Firebase.FirebaseLib.googleServices)
		//classpath("com.google.gms:google-services:4.3.8")
		//com.shov.buildsrc.DependencyInjection.DependencyInjectionUsage.implementDependencyInjectionClasspath1(this)
		classpath(com.shov.buildsrc.DependencyInjection.DependencyInjectionLib.hiltAndroidGradlePlugin)
		//classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")
	}
}