package com.shov.unlimstorage

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
	val gso: GoogleSignInOptions = GoogleSignInOptions
		.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestEmail()
		.build()
}