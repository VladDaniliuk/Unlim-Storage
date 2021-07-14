package com.shov.buildsrc

object DesignVersion {
	const val appcompatVersion = "1.3.0"
	const val materialVersion = "1.4.0"
	const val constraintlayoutVersion = "2.0.4"
}

object DesignLib {
	const val appcompat = "androidx.appcompat:appcompat:${DesignVersion.appcompatVersion}"
	const val material = "com.google.android.material:material:${DesignVersion.materialVersion}"
	const val constraintlayout =
		"androidx.constraintlayout:constraintlayout:${DesignVersion.constraintlayoutVersion}"
}