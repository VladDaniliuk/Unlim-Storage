package com.shov.unlimstorage.repositories

import com.shov.unlimstorage.models.preferences.DropBoxPreferences
import com.shov.unlimstorage.models.preferences.PreferenceManager
import javax.inject.Inject

interface SignInRepository {
	val isLogIn: Boolean
	val getAccessToken: String?
	fun setLogIn(isLogIn: Boolean)
	fun setAccessToken(token: String?)
}

class SignInRepositoryImpl @Inject constructor(
	private val preferenceManager: PreferenceManager,
	private val dropBoxPreferences: DropBoxPreferences
) :
	SignInRepository {
	override val isLogIn: Boolean
		get() = preferenceManager.getAccount
	override val getAccessToken: String?
		get() = dropBoxPreferences.getAccessToken

	override fun setLogIn(isLogIn: Boolean) {
		preferenceManager.setAccount(isLogIn)
	}

	override fun setAccessToken(token: String?) {
		dropBoxPreferences.setAccessToken(token)
	}
}
