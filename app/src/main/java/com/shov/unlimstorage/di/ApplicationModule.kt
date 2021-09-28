package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.filesRepository.*
import com.shov.unlimstorage.models.signInModels.*
import com.shov.unlimstorage.utils.converters.SizeConverter
import com.shov.unlimstorage.utils.converters.SizeConverterImpl
import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.utils.converters.StoreItemConverterImpl
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
	abstract fun provideFilesRepository(filesRepositoryImpl: FilesRepositoryImpl): FilesRepository

	@Binds
	abstract fun provideSizeConverter(sizeConverterImpl: SizeConverterImpl): SizeConverter

	@Binds
	abstract fun provideStoreItemConverter(storeItemConverterImpl: StoreItemConverterImpl):
			StoreItemConverter

	/*@Binds
	@IntoMap
	@MyKey(SignInType.ONEDRIVE)
	abstract fun provideOneDriveFiles(oneDriveFiles: OneDiveFiles):FilesInteractor

	@Binds
	@IntoMap
	@MyKey(SignInType.ONEDRIVE)
	abstract fun provideOneDriveSignIn(oneDriveSignInImpl: OneDriveSignIn): SignInSample*/
}
