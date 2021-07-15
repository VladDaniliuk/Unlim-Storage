package com.shov.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Coroutines {
	object CoroutinesVersion {
		const val kotlinxCoroutinesVersion = "1.5.1"
	}

	object CoroutinesLib {
		const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${
			CoroutinesVersion.kotlinxCoroutinesVersion
		}"
		const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${
			CoroutinesVersion.kotlinxCoroutinesVersion
		}"
	}

	object CoroutinesUsage {
		fun DependencyHandler.implementCoroutines() {
			add("implementation", CoroutinesLib.kotlinxCoroutinesAndroid)
			add("implementation", CoroutinesLib.kotlinxCoroutinesCore)
		}
	}
}