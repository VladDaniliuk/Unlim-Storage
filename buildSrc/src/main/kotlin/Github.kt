import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Github {
	private const val lib = "com.github"
	private const val version = "0.7.2"

	object Lib {
		//lib provides a set of Settings like composable items
		const val composeSettings = "$lib.alorma:compose-settings-ui:$version"
	}
}

fun Project.implementGithub() {
	dependencies {
		implement(Github.Lib.composeSettings)
	}
}