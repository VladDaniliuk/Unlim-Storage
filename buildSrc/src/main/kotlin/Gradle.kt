import org.gradle.kotlin.dsl.DependencyHandlerScope

object GradleVersion {
	const val kotlinGradlePluginVersion = "1.5.21"
	const val gradleVersion = "4.2.1"
}

object GradleLib {
	const val buildGradle = "com.android.tools.build:gradle:${GradleVersion.gradleVersion}"
	const val kotlinGradlePlugin =
		"org.jetbrains.kotlin:kotlin-gradle-plugin:${GradleVersion.kotlinGradlePluginVersion}"
}

object GradleUsage {
	fun DependencyHandlerScope.implementGradle() {
		this.add("implementation", GradleLib.buildGradle)
		this.add("implementation", GradleLib.kotlinGradlePlugin)
	}

	fun implementGradleClasspath(it: DependencyHandlerScope) {
		it {
			add("classpath", GradleLib.buildGradle)
			add("classpath", GradleLib.kotlinGradlePlugin)
		}
	}

	/*fun DependencyHandlerScope.implementGradleClasspath() {
		this {
			add("classpath", GradleLib.buildGradle)
			add("classpath", GradleLib.kotlinGradlePlugin)
		}
	}*/
}