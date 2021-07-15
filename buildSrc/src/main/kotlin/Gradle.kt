package com.shov.buildsrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

object Gradle {
	object GradleVersion {
		const val kotlinGradlePluginVersion = "1.5.21"
		const val gradleVersion = "4.2.1"
	}

	object GradleLib {
		const val buildGradle = "com.android.tools.build:gradle:${GradleVersion.gradleVersion}"
		const val kotlinGradlePlugin =
			"org.jetbrains.kotlin:kotlin-gradle-plugin:${GradleVersion.kotlinGradlePluginVersion}"
	}

	object GradleUsage {
		fun DependencyHandlerScope.implementGradle() {
			add("implementation", GradleLib.buildGradle)
			add("implementation", GradleLib.kotlinGradlePlugin)
		}

		fun DependencyHandlerScope.implementGradleClasspath() {
			add("classpath", GradleLib.buildGradle)
			add("classpath", GradleLib.kotlinGradlePlugin)
		}

		fun implementGradleClasspath1(it: DependencyHandlerScope) {
			it.implementGradle()
		}
	}
}