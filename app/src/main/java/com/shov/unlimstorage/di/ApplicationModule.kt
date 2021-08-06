package com.shov.unlimstorage.di

import com.shov.unlimstorage.models.PreferenceManager
import com.shov.unlimstorage.models.PreferenceManagerImpl
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
internal annotation class MyKey(val value: SignInType)

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

	@Binds
	@IntoMap
	@MyKey(SignInType.GOOGLE)
	abstract fun provideGoogleSignIn(googleSignInImpl: GoogleSignIn): SignInSample

	@Binds
	@IntoMap
	@MyKey(SignInType.BOX)
	abstract fun provideBoxSignIn(boxSignInImpl: BoxSignIn): SignInSample

	@Binds
	@IntoMap
	@MyKey(SignInType.DROPBOX)
	abstract fun provideDropBoxSignIn(dropBoxSignInImpl: DropBoxSignIn): SignInSample

	/*@Binds
	@IntoMap
	@MyKey(SignInType.ONEDRIVE)
	abstract fun provideOneDriveSignIn(oneDriveSignInImpl: OneDriveSignIn): SignInSample*/

	@Binds
	abstract fun provideSignInRepository(signInRepositoryImpl: SignInRepositoryImpl):
			SignInRepository

	@Binds
	abstract fun provideSharedPreferences(preferenceManagerImpl: PreferenceManagerImpl):
			PreferenceManager
}
