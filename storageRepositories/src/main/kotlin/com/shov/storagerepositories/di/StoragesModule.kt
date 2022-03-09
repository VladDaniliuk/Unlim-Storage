package com.shov.storagerepositories.di

import com.shov.boxstorage.BoxFilesDataSource
import com.shov.boxstorage.BoxSignInDataSource
import com.shov.coremodels.models.StorageType
import com.shov.dropboxstorage.datasources.DropBoxFilesDataSource
import com.shov.dropboxstorage.datasources.DropBoxSignInDataSource
import com.shov.googlestorage.GoogleFilesDataSource
import com.shov.googlestorage.GoogleSignInDataSource
import com.shov.storage.FilesDataSource
import com.shov.storage.SignInDataSource
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
interface StoragesModule {
	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	fun bindsBoxFiles(boxFilesDataSource: BoxFilesDataSource): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.BOX)
	fun bindsBoxSignIn(boxSignInDataSource: BoxSignInDataSource): SignInDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	fun bindsDropBoxFiles(dropBoxFilesDataSource: DropBoxFilesDataSource): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.DROPBOX)
	fun bindsDropBoxSignIn(dropBoxSignInDataSource: DropBoxSignInDataSource): SignInDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	fun bindsGoogleFiles(googleFilesDataSource: GoogleFilesDataSource): FilesDataSource

	@Binds
	@IntoMap
	@MyKey(StorageType.GOOGLE)
	fun bindsGoogleSignIn(googleSignInDataSource: GoogleSignInDataSource): SignInDataSource
}