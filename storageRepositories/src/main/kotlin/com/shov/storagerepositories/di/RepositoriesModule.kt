package com.shov.storagerepositories.di

import com.shov.storagerepositories.repositories.files.FileActionsRepository
import com.shov.storagerepositories.repositories.files.FileActionsRepositoryImpl
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import com.shov.storagerepositories.repositories.files.FilesInfoRepositoryImpl
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import com.shov.storagerepositories.repositories.signIn.SignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
	@Binds
	fun bindsFilesInfoRepository(filesInfoRepositoryImpl: FilesInfoRepositoryImpl):
			FilesInfoRepository

	@Binds
	fun bindsNewFileRepository(newFileRepositoryImpl: FileActionsRepositoryImpl):
			FileActionsRepository

	@Binds
	fun bindsSignInRepository(signInRepositoryImpl: SignInRepositoryImpl): SignInRepository
}