import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandler.implement(lib: String) = add("implementation", lib)
fun DependencyHandler.implement(project: ProjectDependency) = add("implementation", project)
fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)
fun DependencyHandlerScope.androidTestImplement(lib: String) = add("androidTestImplementation", lib)
fun DependencyHandlerScope.testImplement(lib: String) = add("testImplementation", lib)

fun Project.implementAll() {
	implementKotlinx()
	implementCore()
	implementGithub()
	implementFirebase()
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
	implementModules()
}

fun Project.implementForGoogleStorage() {
	implementActivity()
	implementHiltForGoogleStorage()
	implementGoogleForGoogleStorage()
	implementModulesForGoogleStorage()
}

fun Project.implementForStorage() {
	implementActivity()
	implementModulesForStorage()
}

fun Project.implementForCoreModels() {
	implementActivity()
	implementIcons()
	implementRoomKtx()
}
