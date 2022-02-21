package com.shov.unlimstorage.di

import com.shov.coremodels.models.StorageType
import com.shov.storage.FilesDataSource
import com.shov.storage.SignInDataSource
import com.shov.unlimstorage.models.repositories.*
import com.shov.unlimstorage.models.repositories.files.*
import com.shov.unlimstorage.models.repositories.signIn.BoxSignIn
import com.shov.unlimstorage.models.repositories.signIn.DropBoxSignIn
import com.shov.unlimstorage.models.repositories.signIn.GoogleSignIn
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
	abstract fun provideBoxFiles(boxFiles: BoxFiles): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	abstract fun provideBoxSignIn(boxSignInImpl: BoxSignIn): SignInDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	abstract fun provideDropBoxFiles(dropBoxFiles: DropBoxFiles): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	abstract fun provideDropBoxSignIn(dropBoxSignInImpl: DropBoxSignIn): SignInDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	abstract fun provideGoogleFiles(googleFiles: GoogleFiles): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	abstract fun provideGoogleSignIn(googleSignInImpl: GoogleSignIn): SignInDataSource

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
