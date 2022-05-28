package com.shov.unlimstorage.models.repositories.signIn

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.android.AuthActivity
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignIn @Inject constructor(
	@ApplicationContext val context: Context,
	private val preference: PreferenceRepository
) : Authorizer {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		Auth.startOAuth2PKCE(
			context,
			Keys.DropBox.APP_KEY,
			DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER)
		)
	}

	override fun isSuccess(result: ActivityResult): Boolean {
		return true//haven't access to this function with dropbox,thus only return true
	}

	override fun isSuccess(): Boolean {
		val credentialPref by preference.getPref(DROPBOX_CREDENTIAL, "")
		return credentialPref.isNotEmpty()
	}

	@Suppress(NEVER_ACCESSED, UNUSED_VALUE)
	override suspend fun signOut() {
		var credentialPref by preference.getPref(DROPBOX_CREDENTIAL, "")

		DbxClientV2(
			DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER),
			DbxCredential.Reader.readFully(credentialPref)
		).auth().tokenRevoke()

		credentialPref = ""
		AuthActivity.result = null//to stop signIn in open dialog when revoke access
	}
}
