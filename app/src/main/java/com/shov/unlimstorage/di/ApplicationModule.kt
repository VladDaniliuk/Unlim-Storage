package com.shov.unlimstorage.di

import com.shov.coremodels.StorageType
import com.shov.unlimstorage.models.repositories.*
import com.shov.unlimstorage.models.repositories.files.*
import com.shov.unlimstorage.models.repositories.signIn.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Target(
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY_GETTER,
	AnnotationTarget.PROPERTY_SETTER
)
@MapKey
internal annotation class MyKey(val value: StorageType)

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	abstract fun provideBoxFiles(boxFiles: BoxFiles): FilesInteractor

	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	abstract fun provideBoxSignIn(boxSignInImpl: BoxSignIn): Authorizer

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	abstract fun provideDropBoxFiles(dropBoxFiles: DropBoxFiles): FilesInteractor

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	abstract fun provideDropBoxSignIn(dropBoxSignInImpl: DropBoxSignIn): Authorizer

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	abstract fun provideGoogleFiles(googleFiles: GoogleFiles): FilesInteractor

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	abstract fun provideGoogleSignIn(googleSignInImpl: GoogleSignIn): Authorizer

	@Binds
	abstract fun provideFilesInfoRepository(filesRepositoryImpl: FilesInfoRepositoryImpl):
			FilesInfoRepository

	@Binds
	abstract fun provideGitHub(gitHubRepositoryImpl: GitHubRepositoryImpl): GitHubRepository

	@Binds
	abstract fun provideDownload(downloadRepositoryImpl: DownloadRepositoryImpl): DownloadRepository

	@Binds
	abstract fun provideNewFileRepository(newFileRepositoryImpl: NewFileRepositoryImpl):
			NewFileRepository

	@Binds
	abstract fun providePreference(preferenceRepositoryImpl: PreferenceRepositoryImpl):
			PreferenceRepository
}
