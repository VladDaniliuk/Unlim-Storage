package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.filesRepository.BoxFiles
import com.shov.unlimstorage.models.filesRepository.DropBoxFiles
import com.shov.unlimstorage.models.filesRepository.FilesInteractor
import com.shov.unlimstorage.models.filesRepository.GoogleFiles
import com.shov.unlimstorage.models.preferences.DropBoxPreferences
import com.shov.unlimstorage.models.preferences.DropBoxPreferencesImpl
import com.shov.unlimstorage.models.preferences.PreferenceManager
import com.shov.unlimstorage.models.preferences.PreferenceManagerImpl
import com.shov.unlimstorage.models.signInModels.*
import com.shov.unlimstorage.repositories.SignInRepository
import com.shov.unlimstorage.repositories.SignInRepositoryImpl
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
	abstract fun provideDropBoxPreferences(dropBoxPreferencesImpl: DropBoxPreferencesImpl):
			DropBoxPreferences

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
	abstract fun provideSharedPreferences(preferenceManagerImpl: PreferenceManagerImpl):
			PreferenceManager

	@Binds
	abstract fun provideSignInRepository(signInRepositoryImpl: SignInRepositoryImpl):
			SignInRepository

	/*@Binds
	@IntoMap
	@MyKey(SignInType.ONEDRIVE)
	abstract fun provideOneDriveFiles(oneDriveFiles: OneDiveFiles):FilesInteractor

	@Binds
	@IntoMap
	@MyKey(SignInType.ONEDRIVE)
	abstract fun provideOneDriveSignIn(oneDriveSignInImpl: OneDriveSignIn): SignInSample*/
}
