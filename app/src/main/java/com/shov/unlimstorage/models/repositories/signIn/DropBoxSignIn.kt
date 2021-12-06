package com.shov.unlimstorage.models.repositories.signIn

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignIn @Inject constructor(
	@ApplicationContext val context: Context
) : Authorizer {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		Auth.startOAuth2PKCE(
			context,
			Keys.DropBox.APP_KEY,
			DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER)
		)
	}

	@Suppress(NEVER_ACCESSED, UNUSED_VALUE)
	override fun isSuccess(result: ActivityResult): Boolean {
		Auth.getOAuth2Token()?.let { token ->
			var isLogIn by Preference(context, IS_AUTH, false)
			var dropBoxToken by Preference(context, DROPBOX_TOKEN, "")

			dropBoxToken = token
			isLogIn = Auth.getUid().isNullOrEmpty().not()

			return isLogIn
		} ?: return false
	}

	override fun isSuccess(): Boolean {
		val dropBoxToken by Preference(context, DROPBOX_TOKEN, "")

		return dropBoxToken.isEmpty().not()
	}

	@Suppress(NEVER_ACCESSED, UNUSED_VALUE)
	override suspend fun signOut() {
		var credentialPref by Preference(context, DROPBOX_TOKEN, "")

		DbxClientV2(
			DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER),
			DbxCredential.Reader.readFully(credentialPref)
		).auth().tokenRevoke()

		credentialPref = ""
	}
}
