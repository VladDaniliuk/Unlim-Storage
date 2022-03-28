package com.shov.unlimstorage.di

import com.shov.autoupdatefeature.models.GitHubDataSource
import com.shov.autoupdatefeature.values.GitHub
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
	@ExperimentalStdlibApi
	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(GitHub.baseUrl)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	@Provides
	@Singleton
	fun provideGitHubApi(retrofit: Retrofit): GitHubDataSource =
		retrofit.create(GitHubDataSource::class.java)
}
