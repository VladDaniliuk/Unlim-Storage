import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Kotlinx {
	private const val lib = "org.jetbrains.kotlinx:kotlinx"
	private const val versionDatetime = "0.3.1"
	private const val versionCoroutines = "1.6.0-RC"

	object Lib {
		const val datetime = "$lib-datetime:$versionDatetime"
		const val coroutinesAndroid = "$lib-coroutines-android:$versionCoroutines"
		const val coroutinesCore = "$lib-coroutines-core:$versionCoroutines"
	}
}

fun Project.implementKotlinx() {
	dependencies {
		implement(Kotlinx.Lib.datetime)
		implement(Kotlinx.Lib.coroutinesAndroid)
		implement(Kotlinx.Lib.coroutinesCore)
	}
}
