package com.shov.unlimstorage.models.signInModels

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class SignInType {
	GOOGLE, BOX, DROPBOX, ONEDRIVE
}

interface Factory {
	fun <T : Authorizer> create(type: SignInType): T
}

@Singleton
class SignInFactory @Inject constructor(
	private val signInTypeMap: Map<SignInType,
			@JvmSuppressWildcards Provider<Authorizer>>
) : Factory {
	override fun <T : SignInSample> create(type: SignInType): T {
		val signInProvider = signInTypeMap[type]
			?: throw IllegalArgumentException("Unknown SignIn class $type")
		return signInProvider.get() as T
	}
}
