package com.shov.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Design {
	object DesignVersion {
		const val appcompatVersion = "1.3.0"
		const val constraintlayoutVersion = "2.0.4"
		const val materialVersion = "1.4.0"
	}

	object DesignLib {
		const val appcompat = "androidx.appcompat:appcompat:${DesignVersion.appcompatVersion}"
		const val constraintlayout =
			"androidx.constraintlayout:constraintlayout:${DesignVersion.constraintlayoutVersion}"
		const val material = "com.google.android.material:material:${DesignVersion.materialVersion}"
	}

	object DesignUsage {
		fun DependencyHandler.implementDesign() {
			add("implementation", DesignLib.appcompat)
			add("implementation", DesignLib.constraintlayout)
			add("implementation", DesignLib.material)
		}
	}
}