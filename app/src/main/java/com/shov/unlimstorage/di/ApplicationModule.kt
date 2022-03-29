package com.shov.unlimstorage.di

import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.datasources.PreferencesDataSourceImpl
import com.shov.unlimstorage.models.repositories.DownloadRepository
import com.shov.unlimstorage.models.repositories.DownloadRepositoryImpl
import com.shov.autoupdatefeature.data.repositories.GitHubRepository
import com.shov.autoupdatefeature.data.repositories.GitHubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
	@Binds
	abstract fun provideGitHub(gitHubRepositoryImpl: GitHubRepositoryImpl): GitHubRepository

	@Binds
	abstract fun provideDownload(downloadRepositoryImpl: DownloadRepositoryImpl): DownloadRepository

	@Binds
	abstract fun providePreference(preferencesDataSourceImpl: PreferencesDataSourceImpl):
			PreferencesDataSource
}
