package com.shov.unlimstorage.models

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.shov.unlimstorage.R
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
			context.getString(R.string.accpunt_preferences),
			Context.MODE_PRIVATE
		)
	override val getAccount: Boolean
		get() = sharedPreferences.getBoolean(context.getString(R.string.is_auth), false)

	override fun setAccount(isAuth: Boolean) {
		sharedPreferences.edit(true) {
			putBoolean(context.getString(R.string.is_auth), isAuth)
		}
	}
}
