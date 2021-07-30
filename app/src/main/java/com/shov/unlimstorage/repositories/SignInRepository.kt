package com.shov.unlimstorage.repositories

import com.shov.unlimstorage.models.PreferenceManager
import javax.inject.Inject

interface SignInRepository {
	val isLogIn: Boolean
	fun setLogIn(isLogIn: Boolean)
}

class SignInRepositoryImpl @Inject constructor(private val preferenceManager: PreferenceManager) :
	SignInRepository {
	override val isLogIn: Boolean
		get() = preferenceManager.getAccount

	override fun setLogIn(isLogIn: Boolean) {
		preferenceManager.setAccount(isLogIn)
	}
}
