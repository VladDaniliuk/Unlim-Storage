import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Squareup {
	private const val lib = "com.squareup"

	object Retrofit {
		private const val version = "2.9.0"
		private const val subLib = "$lib.retrofit2"

		object Lib {
			const val converterMoshi = "$subLib:converter-moshi:$version"
			const val retrofit = "$subLib:retrofit:$version"
			const val converterGson = "$subLib:converter-gson:$version"
		}
	}

	object Okhttp {
		private const val version = "5.0.0-alpha.4"
		private const val subLib = "$lib.okhttp3"

		object Lib {
			const val loggingInterceptor = "$subLib:logging-interceptor:$version"
			const val okhttp = "$subLib:okhttp:$version"
		}
	}

	object Moshi {
		private const val version = "1.12.0"
		private const val subLib = "$lib.moshi:moshi"

		object Lib {
			const val kotlin = "$subLib-kotlin:$version"
			const val kotlinCodegen = "$subLib-kotlin-codegen:$version"
		}
	}
}

fun Project.implementSquareup() {
	dependencies {
		implement(Squareup.Retrofit.Lib.retrofit)
		implement(Squareup.Retrofit.Lib.converterGson)
	}
}
