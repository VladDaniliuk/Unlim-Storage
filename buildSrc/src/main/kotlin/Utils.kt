import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandler.implement(lib: String) = add("implementation", lib)
fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)
fun DependencyHandlerScope.androidTestImplement(lib: String) = add("androidTestImplementation", lib)
fun DependencyHandlerScope.testImplement(lib: String) = add("testImplementation", lib)

fun MutableSet<String>.customExclude() {
	add(BuildType.buildDataProperties)
	add(BuildType.metaInfDependencies)
}

fun Project.implementAll() {
	implementKotlinx()
	implementCore()
	implementGithub()
	implementGoogle()
	implementSquareup()
	implementTest()
	implementLifecycle()
	implementKotlin()
	implementNavigation()
	implementActivity()
	implementCompose()
	implementHilt()
	implementDropbox()
	implementBoxApi()
	implementLocalDataBase()
	implementCoil()
	implementAccompanist()
	implementSecurity()
	implementBiometric()
}
