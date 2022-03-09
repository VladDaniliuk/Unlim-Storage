package com.shov.boxstorage

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.auth.BoxAuthentication
import com.box.androidsdk.content.auth.OAuthActivity
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.storage.SignInDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoxSignInDataSource @Inject constructor(
	@ApplicationContext val context: Context
) : SignInDataSource {
	override fun signIn(): Intent {
		BoxConfig.CLIENT_ID = BOX_CLIENT_ID
		BoxConfig.CLIENT_SECRET = BOX_CLIENT_SECRET

		return OAuthActivity.createOAuthActivityIntent(
			context,
			BoxSession(context),
			BoxAuthentication.isBoxAuthAppAvailable(context)
				.and(BoxSession(context).isEnabledBoxAppAuthentication)
		)
	}

	override fun isSuccess(result: ActivityResult): Boolean =
		BoxSession(context).user?.status == BoxUser.Status.ACTIVE

	override fun isSuccess(): Boolean {
		BoxConfig.CLIENT_ID = BOX_CLIENT_ID
		BoxConfig.CLIENT_SECRET = BOX_CLIENT_SECRET

		return BoxSession(context).user?.status == BoxUser.Status.ACTIVE
	}

	override suspend fun signOut() {
		BoxSession(context).logout()
	}
}
