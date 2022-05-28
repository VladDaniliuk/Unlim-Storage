package com.shov.unlimstorage.models.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shov.unlimstorage.values.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(
	@ApplicationContext private val context: Context,
	private val name: String,
	private val default: T,
	private val isEncrypted: Boolean = false
) : ReadWriteProperty<Any?, T?> {
	private val masterKey: MasterKey by lazy {
		MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
			.setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
	}

	private val sharedPreferences: SharedPreferences by lazy {
		if (isEncrypted) {
			EncryptedSharedPreferences.create(
				context,
				ENCRYPTED_PREFERENCE,
				masterKey,
				EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
				EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
			)
		} else {
			context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
		}
	}

	override fun getValue(thisRef: Any?, property: KProperty<*>): T {
		with(sharedPreferences) {
			@Suppress(UNCHECKED_CAST)
			return when (default) {
				is Long -> getLong(name, default)
				is String -> getString(name, default)
				is Int -> getInt(name, default)
				is Boolean -> getBoolean(name, default)
				is Float -> getFloat(name, default)
				else -> throw UnknownClassInheritance(ARGUMENT_ANY, default.toString())
			} as T
		}
	}

	override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
		with(sharedPreferences.edit()) {
			when (value) {
				is Long -> putLong(name, value)
				is String -> putString(name, value)
				is Int -> putInt(name, value)
				is Boolean -> putBoolean(name, value)
				is Float -> putFloat(name, value)
				else -> throw UnknownClassInheritance(ARGUMENT_ANY, default.toString())
			}.apply()
		}
	}
}
