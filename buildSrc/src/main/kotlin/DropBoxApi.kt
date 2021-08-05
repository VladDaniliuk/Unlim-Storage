import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object DropBoxVersion {
	const val dropboxCoreSdkVersion = "4.0.0"
}

object DropBoxLib {
	const val dropboxCoreSdk =
		"com.dropbox.core:dropbox-core-sdk:${DropBoxVersion.dropboxCoreSdkVersion}"
}

fun Project.implementDropBox() {
	dependencies {
		add("implementation", DropBoxLib.dropboxCoreSdk)
	}
}
