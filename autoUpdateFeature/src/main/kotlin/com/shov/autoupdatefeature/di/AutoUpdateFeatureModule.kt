package com.shov.autoupdatefeature.di

import com.shov.autoupdatefeature.data.repositories.DownloadRepository
import com.shov.autoupdatefeature.data.repositories.DownloadRepositoryImpl
import com.shov.autoupdatefeature.data.repositories.GitHubRepository
import com.shov.autoupdatefeature.data.repositories.GitHubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AutoUpdateFeatureModule {
	@Binds
	fun bindsGitHubRepository(gitHubRepositoryImpl: GitHubRepositoryImpl): GitHubRepository

	@Binds
	fun bindsDownloadRepository(downloadRepositoryImpl: DownloadRepositoryImpl): DownloadRepository
}