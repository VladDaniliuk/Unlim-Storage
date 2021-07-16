import org.gradle.kotlin.dsl.DependencyHandlerScope

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
	fun DependencyHandlerScope.implementCoroutines() {
		add("implementation", CoroutinesLib.kotlinxCoroutinesAndroid)
		add("implementation", CoroutinesLib.kotlinxCoroutinesCore)
	}
}