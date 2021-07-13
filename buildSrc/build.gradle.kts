import Build_gradle.GradleBuild.build_gradle
import Build_gradle.GradleBuild.kotlin_gradle_plugin

object GradleBuild {
	private const val kotlin_version = "1.5.21"
	private const val build_gradle_version = "4.2.1"

	const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
	const val build_gradle = "com.android.tools.build:gradle:$build_gradle_version"
}

dependencies {
	implementation(kotlin_gradle_plugin)
	implementation(build_gradle)
}

plugins {
	`kotlin-dsl`
}

repositories {
	google()
	// The org.jetbrains.kotlin.jvm plugin requires a repository
	// where to download the Kotlin compiler dependencies from.
	mavenCentral()
}