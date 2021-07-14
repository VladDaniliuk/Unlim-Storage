package com.shov.buildsrc

object GradleVersion {
	const val kotlinGradlePluginVersion = "1.5.21"
	const val gradleVersion = "4.2.1"
}

object GradleLib {
	const val gradle = "com.android.tools.build:gradle:${GradleVersion.gradleVersion}"
	const val kotlinGradlePlugin =
		"org.jetbrains.kotlin:kotlin-gradle-plugin:${GradleVersion.kotlinGradlePluginVersion}"
}