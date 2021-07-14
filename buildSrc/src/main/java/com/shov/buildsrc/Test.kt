package com.shov.buildsrc

object TestVersion {
	const val junitVersion = "4.13.2"
	const val extJunitVersion = "1.1.3"
	const val espressoVersion = "3.4.0"
}

object TestLib {
	const val junit = "junit:junit:${TestVersion.junitVersion}"
	const val extJunit = "androidx.test.ext:junit:${TestVersion.extJunitVersion}"
	const val espresso = "androidx.test.espresso:espresso-core:${TestVersion.espressoVersion}"

}