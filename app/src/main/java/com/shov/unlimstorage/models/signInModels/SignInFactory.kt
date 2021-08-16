package com.shov.unlimstorage.models.signInModels

import com.shov.unlimstorage.values.ARGUMENT_SIGN_IN
import com.shov.unlimstorage.values.argumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class StorageType {
	GOOGLE, BOX, DROPBOX, ONEDRIVE
}

interface Factory {
	fun create(type: StorageType): Authorizer
}

@Singleton
class SignInFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<Authorizer>>
) : Factory {
	override fun create(type: StorageType): Authorizer {
		val signInProvider = storageTypeMap[type]
			?: throw argumentException(ARGUMENT_SIGN_IN, type.name)
		return signInProvider.get()
	}
}
