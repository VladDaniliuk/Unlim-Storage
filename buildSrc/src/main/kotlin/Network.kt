import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object NetworkVersion {
	const val moshiKotlinVersion = "1.12.0"
	const val okhttpVersion = "4.9.2"
	const val retrofitVersion = "2.9.0"
	const val gsonVersion = "2.8.9"
}

object NetworkLib {
	const val converterMoshi =
		"com.squareup.retrofit2:converter-moshi:${NetworkVersion.retrofitVersion}"
	const val loggingInterceptor =
		"com.squareup.okhttp3:logging-interceptor:${NetworkVersion.okhttpVersion}"
	const val moshiKotlin =
		"com.squareup.moshi:moshi-kotlin:${NetworkVersion.moshiKotlinVersion}"
	const val moshiKotlinCodegen =
		"com.squareup.moshi:moshi-kotlin-codegen:${NetworkVersion.moshiKotlinVersion}"
	const val okhttp = "com.squareup.okhttp3:okhttp:${NetworkVersion.okhttpVersion}"
	const val retrofit = "com.squareup.retrofit2:retrofit:${NetworkVersion.retrofitVersion}"
	const val gson = "com.google.code.gson:gson:${NetworkVersion.gsonVersion}"
	const val converterGson =
		"com.squareup.retrofit2:converter-gson:${NetworkVersion.retrofitVersion}"
}

fun Project.implementNetwork() {
	dependencies {
		add("implementation", NetworkLib.loggingInterceptor)
		add("implementation", NetworkLib.okhttp)
		add("implementation", NetworkLib.retrofit)
		add("implementation", NetworkLib.gson)
		add("implementation", NetworkLib.converterGson)
	}
}
