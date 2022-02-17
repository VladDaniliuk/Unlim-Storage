package com.shov.unlimstorage.models.repositories.signIn

import com.shov.coremodels.StorageType
import com.shov.unlimstorage.values.ARGUMENT_SIGN_IN
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface Factory {
	fun create(type: StorageType): Authorizer
}

@Singleton
class AuthorizerFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<Authorizer>>
) : Factory {
	override fun create(type: StorageType): Authorizer {
		val signInProvider = storageTypeMap[type]
			?: throw UnknownClassInheritance(ARGUMENT_SIGN_IN, type.name)
		return signInProvider.get()
	}
}
