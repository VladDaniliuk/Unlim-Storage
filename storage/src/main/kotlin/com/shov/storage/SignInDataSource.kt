package com.shov.storage

import android.content.Intent
import androidx.activity.result.ActivityResult

interface SignInDataSource {
	fun isSuccess(): Boolean
	fun isSuccess(result: ActivityResult): Boolean
	fun signIn(): Intent
	suspend fun signOut()
}
