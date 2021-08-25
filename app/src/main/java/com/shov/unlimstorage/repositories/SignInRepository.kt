package com.shov.unlimstorage.repositories

import com.shov.unlimstorage.models.preferences.PreferenceManager
import com.shov.unlimstorage.values.ACCOUNT_PREFERENCES
import com.shov.unlimstorage.values.DROPBOX_TOKEN
import com.shov.unlimstorage.values.IS_AUTH
import javax.inject.Inject

interface SignInRepository {
	val isLogIn: Boolean
	val getDropBoxToken: String?
	fun setLogIn(isLogIn: Boolean)
	fun setDropBoxToken(token: String?)
}

class SignInRepositoryImpl @Inject constructor(
	private val preferenceManager: PreferenceManager
) :
	SignInRepository {
	override val isLogIn: Boolean
		get() = preferenceManager.getBoolean(
			preferencesName = ACCOUNT_PREFERENCES,
			booleanName = IS_AUTH
		)

	override val getDropBoxToken: String?
		get() = preferenceManager.getString(
			preferencesName = ACCOUNT_PREFERENCES,
			stringName = DROPBOX_TOKEN
		)

	override fun setLogIn(isLogIn: Boolean) {
		preferenceManager.putBoolean(
			preferencesName = ACCOUNT_PREFERENCES,
			booleanName = IS_AUTH,
			putValue = isLogIn
		)
	}

	override fun setDropBoxToken(token: String?) {
		preferenceManager.putString(
			preferencesName = ACCOUNT_PREFERENCES,
			stringName = DROPBOX_TOKEN,
			putValue = token
		)
	}
}
