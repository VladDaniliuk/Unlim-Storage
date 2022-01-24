import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object TestVersion {
	const val espressoVersion = "3.4.0"
	const val extJunitVersion = "1.1.3"
	const val junitVersion = "4.13.2"
	const val timberVersion = "5.0.1"
}

object TestLib {
	const val espresso = "androidx.test.espresso:espresso-core:${TestVersion.espressoVersion}"
	const val extJunit = "androidx.test.ext:junit:${TestVersion.extJunitVersion}"
	const val junit = "junit:junit:${TestVersion.junitVersion}"
	const val timber = "com.jakewharton.timber:timber:${TestVersion.timberVersion}"
}

fun Project.implementTest() {
	dependencies {
		androidTestImplement(TestLib.espresso)
		androidTestImplement(TestLib.extJunit)
		testImplement(TestLib.junit)
		implement(TestLib.timber)
	}
}
