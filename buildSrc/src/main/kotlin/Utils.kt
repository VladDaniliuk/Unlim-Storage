import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandler.implement(lib: String, dependency: ExternalModuleDependency.() -> Unit) =
	add("implementation", lib, dependency)

fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)
fun DependencyHandlerScope.androidTestImplement(lib: String) = add("androidTestImplementation", lib)
fun DependencyHandlerScope.implement(lib: String) = add("implementation", lib)
fun DependencyHandlerScope.testImplement(lib: String) = add("testImplementation", lib)
