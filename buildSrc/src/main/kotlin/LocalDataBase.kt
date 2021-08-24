import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object LocalDataBaseVersion {
	const val roomVersion = "2.3.0"
	const val gsonVersion = "2.8.7"
}

object LocalDataBase {
	const val roomRuntime = "androidx.room:room-runtime:${LocalDataBaseVersion.roomVersion}"
	const val roomCompiler = "androidx.room:room-compiler:${LocalDataBaseVersion.roomVersion}"
	const val roomKtx = "androidx.room:room-ktx:${LocalDataBaseVersion.roomVersion}"
	const val gson = "com.google.code.gson:gson:${LocalDataBaseVersion.gsonVersion}"
}

fun Project.implementLocalDataBase() {
	dependencies {
		add("implementation", LocalDataBase.roomRuntime)
		add("kapt", LocalDataBase.roomCompiler)
		add("implementation", LocalDataBase.roomKtx)
		add("implementation", LocalDataBase.gson)
	}
}
