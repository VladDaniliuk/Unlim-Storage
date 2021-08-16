package com.shov.unlimstorage.models.signInModels

import com.shov.unlimstorage.values.ARGUMENT_SIGN_IN
import com.shov.unlimstorage.values.argumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class SignInType {
	GOOGLE, BOX, DROPBOX, ONEDRIVE
}

interface Factory {
	fun create(type: SignInType): SignInSample
}

@Singleton
class SignInFactory @Inject constructor(
	private val signInTypeMap: Map<SignInType,
			@JvmSuppressWildcards Provider<SignInSample>>
) : Factory {
	override fun create(type: SignInType): SignInSample {
		val signInProvider = signInTypeMap[type]
			?: throw argumentException(ARGUMENT_SIGN_IN, type.name)
		return signInProvider.get()
	}
}
