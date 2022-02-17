package com.shov.unlimstorage.models.repositories.signIn

import com.shov.coremodels.StorageType
import com.shov.storage.SignInDataSource
import com.shov.unlimstorage.values.ARGUMENT_SIGN_IN
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface Factory {
	fun create(type: StorageType): SignInDataSource
}

@Singleton
class AuthorizerFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<SignInDataSource>>
) : Factory {
	override fun create(type: StorageType): SignInDataSource {
		val signInProvider = storageTypeMap[type]
			?: throw UnknownClassInheritance(ARGUMENT_SIGN_IN, type.name)
		return signInProvider.get()
	}
}
