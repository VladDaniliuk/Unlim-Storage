package com.shov.buildsrc

object NetworkVersion {
	const val moshiKotlinVersion = "1.12.0"
	const val retofitVersion = "2.9.0"
	const val okhttpVersion = "4.9.1"
}

object NetworkLib {
	const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${NetworkVersion.moshiKotlinVersion}"
	const val moshiKotlinCodegen =
		"com.squareup.moshi:moshi-kotlin-codegen:${NetworkVersion.moshiKotlinVersion}"
	const val retrofit = "com.squareup.retrofit2:retrofit:${NetworkVersion.retofitVersion}"
	const val converterMoshi =
		"com.squareup.retrofit2:converter-moshi:${NetworkVersion.retofitVersion}"
	const val okhttp = "com.squareup.okhttp3:okhttp:${NetworkVersion.okhttpVersion}"
	const val loggingInterceptor =
		"com.squareup.okhttp3:logging-interceptor:${NetworkVersion.okhttpVersion}"
}