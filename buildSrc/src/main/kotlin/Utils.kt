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
	implementSquareup()
	implementTest()
	implementLifecycle()
	implementKotlin()
	implementNavigation()
	implementActivity()
	implementCompose()
	implementHilt()
	implementCoil()
	implementAccompanist()
	implementBiometric()
	implementModules()
}

fun Project.implementForGoogleStorage() {
	implementActivity()
	implementHiltForModules()
	implementGoogleForGoogleStorage()
	implementModulesForChildStorage()
}

fun Project.implementForBoxStorage() {
	implementActivity()
	implementHiltForModules()
	implementBoxApi()
	implementModulesForChildStorage()
}

fun Project.implementForStorage() {
	implementActivity()
	implementModulesForStorage()
}

fun Project.implementForDropBoxStorage() {
	implementActivity()
	implementHiltForModules()
	implementLifecycle()
	implementDropbox()
	implementModulesForDropBoxStorage()
}

fun Project.implementForCoreModels() {
	implementActivity()
	implementIcons()
	implementRoomKtx()
}

fun Project.implementForPreferences() {
	implementHiltForModules()
	implementSecurity()
}

fun Project.implementForLocalStorage() {
	implementLocalDataBase()
	implementModulesForLocalStorage()
	implementHiltForLocalStorage()
}
