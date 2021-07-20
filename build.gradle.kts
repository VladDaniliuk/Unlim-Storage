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
}