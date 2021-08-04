import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object CoreVersion {
	const val coreKtxVersion = "1.6.0"
}

object CoreLib {
	const val coreKtx = "androidx.core:core-ktx:${CoreVersion.coreKtxVersion}"
}

fun Project.implementCore() {
	dependencies {
		add("implementation", CoreLib.coreKtx)
	}
}
