package com.shov.unlimstorage.models.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.shov.unlimstorage.values.DROPBOX_PREFERENCES
import com.shov.unlimstorage.values.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface DropBoxPreferences {
	val accountPreferences: SharedPreferences
	val getAccessToken: String?
	fun setAccessToken(token: String?)
}

class DropBoxPreferencesImpl @Inject constructor(@ApplicationContext private val context: Context) :
	DropBoxPreferences {
	override val accountPreferences: SharedPreferences
		get() = context.getSharedPreferences(
			DROPBOX_PREFERENCES,
			Context.MODE_PRIVATE
		)

	override val getAccessToken: String?
		get() = accountPreferences.getString(TOKEN, null)

	override fun setAccessToken(token: String?) {
		accountPreferences.edit(true) {
			putString(TOKEN, token)
		}
	}
}
