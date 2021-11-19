import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandlerDelegate.implement(lib: String) = add("implementation", lib)

fun DependencyHandlerDelegate.kapt(lib: String) = add("kapt", lib)