package com.shov.storagerepositories.repositories.signIn

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.shov.coremodels.models.StorageType
import com.shov.storagerepositories.repositories.factories.SignInFactory
import javax.inject.Inject

interface SignInRepository {
	fun isSuccess(storageType: StorageType): Boolean
	fun isSuccess(storageType: StorageType, result: ActivityResult): Boolean
	fun signIn(storageType: StorageType): Intent
	suspend fun signOut(storageType: StorageType)
}

class SignInRepositoryImpl @Inject constructor(
	private val signInFactory: SignInFactory
) : SignInRepository {
	override fun isSuccess(storageType: StorageType): Boolean =
		signInFactory.create(storageType).isSuccess()

	override fun isSuccess(storageType: StorageType, result: ActivityResult): Boolean =
		signInFactory.create(storageType).isSuccess(result)

	override fun signIn(storageType: StorageType): Intent =
		signInFactory.create(storageType).signIn()

	override suspend fun signOut(storageType: StorageType) {
		signInFactory.create(storageType).signOut()
	}
}