import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object CoroutinesVersion {
	const val kotlinxCoroutinesVersion = "1.5.2"
}

object CoroutinesLib {
	const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${
		CoroutinesVersion.kotlinxCoroutinesVersion
	}"
	const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${
		CoroutinesVersion.kotlinxCoroutinesVersion
	}"
}

fun Project.implementCoroutines() {
	dependencies {
		add("implementation", CoroutinesLib.kotlinxCoroutinesAndroid)
		add("implementation", CoroutinesLib.kotlinxCoroutinesCore)
	}
}
