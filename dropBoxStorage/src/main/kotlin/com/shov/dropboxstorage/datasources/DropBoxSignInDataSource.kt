package com.shov.dropboxstorage.datasources

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.AuthActivity
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.shov.dropboxstorage.DropBoxAuthActivity
import com.shov.dropboxstorage.values.CLIENT_IDENTIFIER
import com.shov.dropboxstorage.values.DROPBOX_CREDENTIAL
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.storage.SignInDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignInDataSource @Inject constructor(
	@ApplicationContext val context: Context,
	private val preferences: PreferencesDataSource
) : SignInDataSource {
	override fun signIn(): Intent = Intent(context, DropBoxAuthActivity::class.java)

	override fun isSuccess(result: ActivityResult): Boolean = if (result.resultCode == RESULT_OK) {
		var credentialPref by preferences.getPref(DROPBOX_CREDENTIAL, "")
		credentialPref = result.data?.extras?.getString(DROPBOX_CREDENTIAL) ?: ""
		credentialPref.isNotEmpty()
	} else false

	override fun isSuccess(): Boolean {
		val credentialPref by preferences.getPref(DROPBOX_CREDENTIAL, "")
		return credentialPref.isNotEmpty()
	}

	@Suppress("UNUSED_VALUE")
	override suspend fun signOut() {
		var credentialPref by preferences.getPref(DROPBOX_CREDENTIAL, "")

		DbxClientV2(
			DbxRequestConfig(CLIENT_IDENTIFIER),
			DbxCredential.Reader.readFully(credentialPref)
		).auth().tokenRevoke()

		credentialPref = ""
		AuthActivity.result = null//to stop signIn in open dialog when revoke access
	}
}
