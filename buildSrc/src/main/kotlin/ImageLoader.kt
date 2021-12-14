import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object ImageLoaderVersion {
	const val coilVersion = "1.4.0"
}

object ImageLoaderLib {
	const val coil = "io.coil-kt:coil-compose:${ImageLoaderVersion.coilVersion}"
}

fun Project.implementImageLoader() {
	dependencies {
		add("implementation", ImageLoaderLib.coil)
	}
}
