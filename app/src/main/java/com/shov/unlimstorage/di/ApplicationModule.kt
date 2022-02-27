package com.shov.unlimstorage.di

import com.shov.boxstorage.BoxFilesDataSource
import com.shov.boxstorage.BoxSignInDataSource
import com.shov.coremodels.models.StorageType
import com.shov.googlestorage.GoogleFilesDataSource
import com.shov.googlestorage.GoogleSignInDataSource
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.preferences.datasources.PreferencesDataSourceImpl
import com.shov.storage.FilesDataSource
import com.shov.storage.SignInDataSource
import com.shov.unlimstorage.models.repositories.*
import com.shov.unlimstorage.models.repositories.files.*
import com.shov.unlimstorage.models.repositories.signIn.DropBoxSignIn
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
	abstract fun provideBoxFiles(boxFiles: BoxFilesDataSource): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	abstract fun provideBoxSignIn(boxSignInImpl: BoxSignInDataSource): SignInDataSource

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
	abstract fun provideGoogleFiles(googleFiles: GoogleFilesDataSource): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	abstract fun provideGoogleSignIn(googleSignInImpl: GoogleSignInDataSource): SignInDataSource

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
	abstract fun providePreference(preferencesDataSourceImpl: PreferencesDataSourceImpl):
			PreferencesDataSource
}
