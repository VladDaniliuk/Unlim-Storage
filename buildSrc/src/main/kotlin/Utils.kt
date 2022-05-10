import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandler.implement(lib: String) = add("implementation", lib)
fun DependencyHandler.implement(project: ProjectDependency) = add("implementation", project)
fun DependencyHandler.implement(lib: String, dependency: ExternalModuleDependency.() -> Unit) =
	add("implementation", lib, dependency)

fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)
fun DependencyHandlerDelegate.ksp(lib: String) = add("ksp", lib)
fun DependencyHandlerScope.androidTestImplement(lib: String) = add("androidTestImplementation", lib)
fun DependencyHandlerScope.testImplement(lib: String) = add("testImplementation", lib)

fun Project.implementAll() {
	implementAccompanist()
	implementAccompanistNavigation()
	implementActivity()
	implementBiometric()
	implementCoil()
	implementCompose()
	implementCore()
	implementHilt()
	implementKotlin()
	implementKotlinx()
	implementLifecycle()
	implementMaterial3()
	implementModules()
	implementNavigation()
	implementSquareup()
	implementTest()
}

fun Project.implementForAutoUpdateFeature() {
	implementKotlin()
	implementActivity()
	implementComposeForModules()
	implementHiltForFeatureModules()
	implementLifecycle()
	implementMaterial3()
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
	implementAccompanist()
	implementAccompanistNavigation()
	implementComposeForModules()
	implementHiltForFeatureModules()
	implementKotlin()
	implementKotlinx()
	implementLifecycle()
	implementMaterial3()
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

fun Project.implementForFilesFeature() {
	implementAccompanistNavigation()
	implementCoil()
	implementComposeForModules()
	implementHiltForFeatureModules()
	implementIcons()
	implementKotlin()
	implementLifecycle()
	implementMaterial3()
	implementModulesForFilesFeature()
	implementSwipeRefresh()
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
	implementMaterial3()
	implementHiltForFeatureModules()
	implementIcons()
	implementLifecycle()
	implementModulesForSettingsFeature()
}

fun Project.implementForSignInFeature() {
	implementComposeForModules()
	implementHiltForFeatureModules()
	implementKotlin()
	implementLifecycle()
	implementMaterial3()
	implementModulesForSignInFeature()
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
