package com.shov.storagerepositories.repositories.factories

import com.shov.coremodels.models.StorageType
import com.shov.storage.SignInDataSource
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

internal interface SignInFactoryInterface {
	fun create(type: StorageType): SignInDataSource
}

@Singleton
class SignInFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<SignInDataSource>>
) : SignInFactoryInterface {
	override fun create(type: StorageType): SignInDataSource {
		val signInProvider = storageTypeMap[type]
			?: throw Exception("Unknown SignInDataSource class $type")
		return signInProvider.get()
	}
}
