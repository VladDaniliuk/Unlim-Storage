package com.shov.unlimstorage

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.shov.unlimstorage.ui.SignInButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val viewModel: SignInViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SignInButton {
				val mGoogleSignInClient = GoogleSignIn.getClient(this, viewModel.gso)
				startForResult.launch(mGoogleSignInClient.signInIntent)
			}
		}
	}

	private val startForResult = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		if (result.resultCode == Activity.RESULT_OK) {
			Timber.v("Ok")
		}
	}
}