package com.shov.unlimstorage.models.signInModels

import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.ARGUMENT_SIGN_IN
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class StorageType(val imageId: Int, val nameId: Int) {
	GOOGLE(R.drawable.ic_google_drive, R.string.google),
	BOX(R.drawable.ic_box, R.string.box),
	DROPBOX(R.drawable.ic_drop_box, R.string.dropbox),
	//ONEDRIVE(R.drawable.ic_one_drive, R.string.onedrive)
}

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
