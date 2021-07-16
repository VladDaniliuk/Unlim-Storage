import org.gradle.kotlin.dsl.DependencyHandlerScope

object CoreVersion {
	const val coreKtxVersion = "1.6.0"
}

object CoreLib {
	const val coreKtx = "androidx.core:core-ktx:${CoreVersion.coreKtxVersion}"
}

object CoreUsage {
	fun DependencyHandlerScope.implementCore() {
		this.add("implementation", CoreLib.coreKtx)
	}
}