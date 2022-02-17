package com.shov.storage

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

interface SignInDataSource {
	fun signIn(dataForSignIn: ManagedActivityResultLauncher<Intent, ActivityResult>)
	fun isSuccess(result: ActivityResult): Boolean
	fun isSuccess(): Boolean
	suspend fun signOut()
}
