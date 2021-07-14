package com.shov.buildsrc

object CoroutinesVersion {
	const val kotlinxCoroutinesVersion = "1.5.1"
}

object CoroutinesLib {
	const val kotlinxCoroutinesCore =
		"org.jetbrains.kotlinx:kotlinx-coroutines-core:${
			CoroutinesVersion.kotlinxCoroutinesVersion
		}"
	const val kotlinxCoroutinesAndroid =
		"org.jetbrains.kotlinx:kotlinx-coroutines-android:${
			CoroutinesVersion.kotlinxCoroutinesVersion
		}"
}
