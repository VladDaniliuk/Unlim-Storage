package com.shov.unlimstorage.models.signInModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

interface SignInSample {
	fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>)
	fun isSuccess(result: ActivityResult): Boolean
	fun isSuccess(): Boolean
	fun signOut():Boolean
}
