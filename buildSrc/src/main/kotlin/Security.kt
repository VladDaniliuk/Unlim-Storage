import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Security {
	private const val version = "1.1.0-alpha03"
	private const val lib = "androidx.security:security-crypto:"

	object Lib {
		const val crypto = "$lib$version"
	}
}

fun Project.implementSecurity() {
	dependencies {
		implement(Security.Lib.crypto)
	}
}
