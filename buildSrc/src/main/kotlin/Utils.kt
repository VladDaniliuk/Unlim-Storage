import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandler.implement(lib: String) = add("implementation", lib)
fun DependencyHandler.implement(project: ProjectDependency) = add("implementation", project)
fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)
fun DependencyHandlerDelegate.ksp(lib: String) = add("ksp", lib)
fun DependencyHandlerScope.androidTestImplement(lib: String) = add("androidTestImplementation", lib)
fun DependencyHandlerScope.testImplement(lib: String) = add("testImplementation", lib)

fun Project.implementAll() {
	implementKotlinx()
	implementCore()
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

fun Project.implementForAutoUpdateFeature() {
	implementKotlin()
	implementActivity()
	implementComposeForModules()
	implementHiltForHiltModules()
	implementLifecycle()
	implementModulesForAutoUpdateFeature()
	implementSquareup()
	implementIcons()
}

fun Project.implementForBoxStorage() {
	implementKotlin()
	implementActivity()
	implementHiltForModules()
	implementBoxApi()
	implementModulesForChildStorage()
}

fun Project.implementForCoreModels() {
	implementKotlin()
	implementActivity()
	implementIcons()
	implementRoomKtx()
}

fun Project.implementForCoreUi() {
	implementKotlin()
	implementKotlinx()
	implementLifecycle()
	implementHiltForViewModels()
	implementComposeForModules()
	implementModulesForCoreUI()
}

fun Project.implementForCoreUtils() {
	implementKotlin()
	implementKotlinx()
	implementActivity()
	implementHiltForViewModels()
}

fun Project.implementForDropBoxStorage() {
	implementKotlin()
	implementActivity()
	implementHiltForModules()
	implementLifecycle()
	implementDropbox()
	implementModulesForDropBoxStorage()
}

fun Project.implementForGoogleStorage() {
	implementKotlin()
	implementActivity()
	implementHiltForModules()
	implementGoogleForGoogleStorage()
	implementModulesForChildStorage()
}

fun Project.implementForLocalStorage() {
	implementKotlin()
	implementLocalDataBase()
	implementModulesForLocalStorage()
	implementHiltForHiltModules()
}

fun Project.implementForPreferences() {
	implementKotlin()
	implementHiltForHiltModules()
	implementSecurity()
}

fun Project.implementForSettingsFeature() {
	implementKotlin()
	implementBiometric()
	implementComposeForModules()
	implementHiltForFeatureModules()
	implementIcons()
	implementLifecycle()
	implementModulesForSettingsFeature()
}

fun Project.implementForSignInFeature() {
	implementKotlin()
	implementModulesForSignInFeature()
	implementComposeForModules()
	implementLifecycle()
	implementHiltForFeatureModules()
}

fun Project.implementForStorage() {
	implementKotlin()
	implementActivity()
	implementModulesForStorage()
}

fun Project.implementForStorageRepositories() {
	implementKotlin()
	implementHiltForHiltModules()
	implementModulesForStorageRepositories()
	implementKotlinx()
}
