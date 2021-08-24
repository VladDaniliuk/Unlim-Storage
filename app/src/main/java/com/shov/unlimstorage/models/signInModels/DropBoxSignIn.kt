package com.shov.unlimstorage.models.signInModels

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.android.Auth
import com.dropbox.core.android.AuthActivity
import com.shov.unlimstorage.repositories.SignInRepository
import com.shov.unlimstorage.values.DropBox
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignIn @Inject constructor(
	@ApplicationContext val context: Context,
	private val signInRepository: SignInRepository
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
		signInRepository.setAccessToken(Auth.getOAuth2Token())
		return Auth.getUid()?.isNotEmpty() ?: false
	}

	override fun isSuccess(): Boolean {
		return signInRepository.getAccessToken.isNullOrEmpty().not()
	}

	override fun signOut(): Boolean {
		signInRepository.setAccessToken(null)
		return isSuccess()
	}
}
