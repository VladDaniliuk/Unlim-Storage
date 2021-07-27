package com.shov.unlimstorage

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.services.drive.DriveScopes
import com.shov.unlimstorage.values.NULL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
	val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions
		.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
		.requestScopes(Scope(DriveScopes.DRIVE_FILE))
		.requestEmail()
		.build()

	private val _googleAccess = MutableLiveData(NULL)
	val googleAccess: LiveData<String>
		get() = _googleAccess

	fun checkAccess(result: ActivityResult) {
		if (result.resultCode == Activity.RESULT_OK) {
			val task: Task<GoogleSignInAccount> =
				GoogleSignIn.getSignedInAccountFromIntent(result.data)

			if (task.isSuccessful) {
				updateUserInfo(task.getResult(ApiException::class.java))
			}
		} else {
			updateUserInfo(null)
		}
	}

	fun updateUserInfo(account: GoogleSignInAccount?) {
		account?.let {
			_googleAccess.postValue("${it.displayName}\n${it.email}\n${it.grantedScopes}")
		} ?: run {
			_googleAccess.postValue(NULL)
		}
	}
}