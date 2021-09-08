package com.shov.unlimstorage.models.signInModels

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.auth.BoxAuthentication
import com.box.androidsdk.content.auth.OAuthActivity
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.Box
import com.shov.unlimstorage.values.IS_AUTH
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoxSignIn @Inject constructor(@ApplicationContext val context: Context) : Authorizer {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		BoxConfig.CLIENT_ID = Box.CLIENT_ID
		BoxConfig.CLIENT_SECRET = Box.CLIENT_SECRET

		val intent = OAuthActivity.createOAuthActivityIntent(
			context,
			BoxSession(context),
			BoxAuthentication.isBoxAuthAppAvailable(context)
				.and(BoxSession(context).isEnabledBoxAppAuthentication)
		)

		dataForSignIn.launch(intent)
	}

	override fun isSuccess(result: ActivityResult): Boolean {
		var isLogIn by Preference(context, IS_AUTH, false)

		isLogIn = BoxSession(context).user?.let { user ->
			user.status == BoxUser.Status.ACTIVE
		} ?: false

		return isLogIn
	}

	override fun isSuccess(): Boolean {
		BoxConfig.CLIENT_ID = Box.CLIENT_ID
		BoxConfig.CLIENT_SECRET = Box.CLIENT_SECRET

		return BoxSession(context).user?.let { user ->
			user.status?.let { status ->
				status == BoxUser.Status.ACTIVE
			} ?: false
		} ?: false
	}

	override fun signOut(): Boolean = BoxSession(context).logout().isDone
}
