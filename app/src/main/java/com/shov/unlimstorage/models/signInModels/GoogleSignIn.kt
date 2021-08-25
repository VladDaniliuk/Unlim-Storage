package com.shov.unlimstorage.models.signInModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleSignIn @Inject constructor(@ApplicationContext val context: Context) : Authorizer {
	override fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>) {
		val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions
			.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
			.requestScopes(Scope(DriveScopes.DRIVE_FILE))
			.requestEmail()
			.build()

		val mGoogleSignInClient =
			GoogleSignIn.getClient(
				context,
				googleSignInOptions
			)

		dataForSignIn.launch(mGoogleSignInClient.signInIntent)
	}

	override fun isSuccess(result: ActivityResult): Boolean {
		return if (result.resultCode == Activity.RESULT_OK)
			GoogleSignIn.getSignedInAccountFromIntent(result.data).isSuccessful
		else false
	}

	override fun isSuccess(): Boolean =
		GoogleSignIn.getLastSignedInAccount(context)?.let { google ->
			google.account != null
		} ?: false

	override fun signOut(): Boolean {
		val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions
			.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
			.requestScopes(Scope(DriveScopes.DRIVE_FILE))
			.requestEmail()
			.build()

		val mGoogleSignInClient =
			GoogleSignIn.getClient(
				context,
				googleSignInOptions
			)

		return mGoogleSignInClient.signOut().isComplete
	}
}
