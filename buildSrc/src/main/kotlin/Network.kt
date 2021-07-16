import org.gradle.kotlin.dsl.DependencyHandlerScope

object NetworkVersion {
	const val moshiKotlinVersion = "1.12.0"
	const val okhttpVersion = "4.9.1"
	const val retofitVersion = "2.9.0"
}

object NetworkLib {
	const val converterMoshi =
		"com.squareup.retrofit2:converter-moshi:${NetworkVersion.retofitVersion}"
	const val loggingInterceptor =
		"com.squareup.okhttp3:logging-interceptor:${NetworkVersion.okhttpVersion}"
	const val moshiKotlin =
		"com.squareup.moshi:moshi-kotlin:${NetworkVersion.moshiKotlinVersion}"
	const val moshiKotlinCodegen =
		"com.squareup.moshi:moshi-kotlin-codegen:${NetworkVersion.moshiKotlinVersion}"
	const val okhttp = "com.squareup.okhttp3:okhttp:${NetworkVersion.okhttpVersion}"
	const val retrofit = "com.squareup.retrofit2:retrofit:${NetworkVersion.retofitVersion}"
}

object NetworkUsage {
	fun DependencyHandlerScope.implementNetwork() {
		add("implementation", NetworkLib.converterMoshi)
		add("implementation", NetworkLib.loggingInterceptor)
		add("implementation", NetworkLib.moshiKotlin)
		add("kapt", NetworkLib.moshiKotlinCodegen)
		add("implementation", NetworkLib.okhttp)
		add("implementation", NetworkLib.retrofit)
	}
}