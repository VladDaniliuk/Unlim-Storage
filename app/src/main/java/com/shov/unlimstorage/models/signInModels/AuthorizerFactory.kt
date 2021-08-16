package com.shov.unlimstorage.models.signInModels

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class StorageType {
	GOOGLE, BOX, DROPBOX, ONEDRIVE
}

interface Factory {
	fun <T : Authorizer> create(type: StorageType): T
}

@Singleton
class AuthorizerFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<Authorizer>>
) : Factory {
	override fun <T : Authorizer> create(type: StorageType): T {
		val signInProvider = storageTypeMap[type]
			?: throw IllegalArgumentException("Unknown SignIn class $type")
		return signInProvider.get() as T
	}
}
