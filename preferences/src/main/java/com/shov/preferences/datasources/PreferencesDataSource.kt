package com.shov.preferences.datasources

import android.content.Context
import com.shov.preferences.models.Preference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PreferencesDataSource {
	fun <T> getPref(name: String, default: T): Preference<T>
	fun <T> getEncryptedPref(name: String, default: T): Preference<T>
}

class PreferencesDataSourceImpl @Inject constructor(
	@ApplicationContext val context: Context
) : PreferencesDataSource {
	override fun <T> getPref(name: String, default: T) = Preference(context, name, default)

	override fun <T> getEncryptedPref(name: String, default: T) =
		Preference(context, name, default, true)
}
