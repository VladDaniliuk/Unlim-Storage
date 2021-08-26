package com.shov.unlimstorage.models.signInModels

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.android.Auth
import com.dropbox.core.android.AuthActivity
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignIn @Inject constructor(
	@ApplicationContext val context: Context
) : Authorizer {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		val intent = AuthActivity.makeIntent(
			context,
			DropBox.APP_KEY,
			DropBox.WEB_HOST,
			DropBox.API_TYPE
		)
		dataForSignIn.launch(intent)
	}

	@Suppress(NEVER_ACCESSED, UNUSED_VALUE)
	override fun isSuccess(result: ActivityResult): Boolean {
		Auth.getOAuth2Token()?.let { token ->
			var isLogIn by Preference(context, IS_AUTH, false)
			var dropBoxToken by Preference(context, DROPBOX_TOKEN, DEFAULT_STRING)

			dropBoxToken = token
			isLogIn = Auth.getUid().isNullOrEmpty().not()

			return isLogIn
		} ?: return false
	}

	override fun isSuccess(): Boolean {
		val dropBoxToken by Preference(context, DROPBOX_TOKEN, DEFAULT_STRING)

		return dropBoxToken.isEmpty().not()
	}

	@Suppress(NEVER_ACCESSED, UNUSED_VALUE)
	override fun signOut(): Boolean {
		var dropBoxToken by Preference(context, DROPBOX_TOKEN, DEFAULT_STRING)

		dropBoxToken = DEFAULT_STRING

		return isSuccess()
	}
}
