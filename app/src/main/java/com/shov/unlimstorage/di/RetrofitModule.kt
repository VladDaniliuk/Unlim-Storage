package com.shov.unlimstorage.di

import com.google.gson.GsonBuilder
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.api.services.GitHubApi
import com.shov.unlimstorage.utils.converters.LastReleaseDeserializer
import com.shov.unlimstorage.values.GitHub
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
	@ExperimentalStdlibApi
	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(GitHub.baseUrl)
		.addConverterFactory(
			GsonConverterFactory.create(
				GsonBuilder().registerTypeAdapter(
					typeOf<LastReleaseItem>().javaType,
					LastReleaseDeserializer()
				).create()
			)
		)
		.build()

	@Provides
	@Singleton
	fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)
}
