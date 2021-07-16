import org.gradle.kotlin.dsl.DependencyHandlerScope

object TestVersion {
	const val espressoVersion = "3.4.0"
	const val extJunitVersion = "1.1.3"
	const val junitVersion = "4.13.2"
}

object TestLib {
	const val espresso = "androidx.test.espresso:espresso-core:${TestVersion.espressoVersion}"
	const val extJunit = "androidx.test.ext:junit:${TestVersion.extJunitVersion}"
	const val junit = "junit:junit:${TestVersion.junitVersion}"
}

object TestUsage {
	fun DependencyHandlerScope.implementTest() {
		add("androidTestImplementation", TestLib.espresso)
		add("androidTestImplementation", TestLib.extJunit)
		add("testImplementation", TestLib.junit)
	}
}