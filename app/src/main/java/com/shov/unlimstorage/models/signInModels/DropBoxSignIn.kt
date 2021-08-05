package com.shov.unlimstorage.models.signInModels

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.dropbox.core.android.Auth
import com.dropbox.core.android.AuthActivity
import com.shov.unlimstorage.values.DropBox
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxSignIn @Inject constructor(@ApplicationContext val context: Context) : SignInSample {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		val intent = AuthActivity.makeIntent(
			context,
			DropBox.APP_KEY,
			DropBox.WEB_HOST,
			DropBox.API_TYPE
		)
		dataForSignIn.launch(intent)
	}

	override fun isSuccess(result: ActivityResult): Boolean = Auth.getUid()?.isNotEmpty() ?: false
}
