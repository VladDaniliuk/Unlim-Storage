package com.shov.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Core {
	object CoreVersion {
		const val coreKtxVersion = "1.6.0"
	}

	object CoreLib {
		const val coreKtx = "androidx.core:core-ktx:${CoreVersion.coreKtxVersion}"
	}

	object CoreUsage {
		fun DependencyHandler.implementCore() {
			this.add("implementation", CoreLib.coreKtx)
		}

		fun implementCore1(d: DependencyHandler) {
			d.implementCore()
		}
	}
}