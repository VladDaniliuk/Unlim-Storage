package com.shov.unlimstorage.models.repositories

import android.content.Context
import com.shov.unlimstorage.models.preferences.Preference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PreferenceRepository {
	fun <T> getPref(name: String, default: T): Preference<T>
	fun <T> getEncryptedPref(name: String, default: T): Preference<T>
}

class PreferenceRepositoryImpl @Inject constructor(
	@ApplicationContext val context: Context
) : PreferenceRepository {
	override fun <T> getPref(name: String, default: T) = Preference(context, name, default)

	override fun <T> getEncryptedPref(name: String, default: T) =
		Preference(context, name, default, true)
}
