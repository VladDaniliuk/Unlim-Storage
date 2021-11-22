import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Room {
	private const val lib = "androidx.room:room"
	private const val roomVersion = "2.3.0"

	object Lib {
		const val runtime = "$lib-runtime:$roomVersion"
		const val compiler = "$lib-compiler:$roomVersion"
		const val ktx = "$lib-ktx:$roomVersion"
	}
}

fun Project.implementLocalDataBase() {
	dependencies {
		implement(Room.Lib.runtime)
		kapt(Room.Lib.compiler)
		implement(Room.Lib.ktx)
	}
}
