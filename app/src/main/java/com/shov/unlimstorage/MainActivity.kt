package com.shov.unlimstorage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.shov.unlimstorage.ui.SignInButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val viewModel: SignInViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.updateUserInfo(GoogleSignIn.getLastSignedInAccount(this))

		setContent {
			val counterState = remember { viewModel.googleAccess }

			SignInButton {
				val mGoogleSignInClient =
					GoogleSignIn.getClient(this, viewModel.googleSignInOptions)
				startForResult.launch(mGoogleSignInClient.signInIntent)
			}
			Text(text = counterState.value)
		}
	}

	private val startForResult = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		viewModel.checkAccess(result)
	}
}