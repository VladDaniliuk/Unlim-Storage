import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Dropbox {
	const val version = "5.0.0"
	const val lib = "com.dropbox.core:dropbox-core-sdk:"

	object Lib {
		const val dropbox = "$lib$version"
	}
}

fun Project.implementDropbox() {
	dependencies {
		implement(Dropbox.Lib.dropbox)
	}
}
