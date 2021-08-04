package com.shov.unlimstorage.models

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.shov.unlimstorage.values.ACCOUNT_PREFERENCES
import com.shov.unlimstorage.values.IS_AUTH
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PreferenceManager {
	val sharedPreferences: SharedPreferences
	val getAccount: Boolean
	fun setAccount(isAuth: Boolean)
}

class PreferenceManagerImpl @Inject constructor(@ApplicationContext private val context: Context) :
	PreferenceManager {
	override val sharedPreferences: SharedPreferences
		get() = context.getSharedPreferences(
			ACCOUNT_PREFERENCES,
			Context.MODE_PRIVATE
		)
	override val getAccount: Boolean
		get() = sharedPreferences.getBoolean(IS_AUTH, false)

	override fun setAccount(isAuth: Boolean) {
		sharedPreferences.edit(true) {
			putBoolean(IS_AUTH, isAuth)
		}
	}
}
