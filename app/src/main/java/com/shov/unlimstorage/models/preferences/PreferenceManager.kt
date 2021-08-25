package com.shov.unlimstorage.models.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PreferenceManager {
	fun getString(preferencesName: String, stringName: String): String?
	fun putString(preferencesName: String, stringName: String, putValue: String?)
	fun getBoolean(preferencesName: String, booleanName: String): Boolean
	fun putBoolean(preferencesName: String, booleanName: String, putValue: Boolean)
}

class PreferenceManagerImpl @Inject constructor(@ApplicationContext private val context: Context) :
	PreferenceManager {
	private fun accountPreferences(preferencesName: String): SharedPreferences {
		return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
	}

	override fun getString(preferencesName: String, stringName: String): String? {
		return accountPreferences(preferencesName).getString(stringName, null)
	}

	override fun putString(preferencesName: String, stringName: String, putValue: String?) {
		accountPreferences(preferencesName).edit(true) {
			putString(stringName, putValue)
		}
	}

	override fun getBoolean(preferencesName: String, booleanName: String): Boolean {
		return accountPreferences(preferencesName).getBoolean(booleanName, false)
	}

	override fun putBoolean(preferencesName: String, booleanName: String, putValue: Boolean) {
		accountPreferences(preferencesName).edit(true) {
			putBoolean(booleanName, putValue)
		}
	}
}
