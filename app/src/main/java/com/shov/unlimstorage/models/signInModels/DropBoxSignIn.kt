package com.shov.unlimstorage.models.signInModels

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.android.Auth
import com.dropbox.core.android.AuthActivity
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.DROPBOX_TOKEN
import com.shov.unlimstorage.values.DropBox
import com.shov.unlimstorage.values.IS_AUTH
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

	override fun isSuccess(result: ActivityResult): Boolean {
		var isLogIn by Preference(context, IS_AUTH, false)
		var dropBoxToken by Preference(context, DROPBOX_TOKEN, "")

		dropBoxToken = Auth.getOAuth2Token()
		isLogIn = Auth.getUid().isNullOrEmpty().not()

		return isLogIn
	}

	override fun isSuccess(): Boolean {
		val dropBoxToken by Preference(context, DROPBOX_TOKEN, "")

		return dropBoxToken.isEmpty().not()
	}

	override fun signOut(): Boolean {
		var dropBoxToken by Preference(context, DROPBOX_TOKEN, "")

		dropBoxToken = ""

		return isSuccess()
	}
}
