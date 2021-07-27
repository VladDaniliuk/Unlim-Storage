package com.shov.unlimstorage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.shov.unlimstorage.ui.SignInButton
import com.shov.unlimstorage.values.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val viewModel: SignInViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.updateUserInfo(GoogleSignIn.getLastSignedInAccount(this))

		setContent {
			val counterState by viewModel.googleAccess.observeAsState()
			remember { viewModel.googleAccess }

			SignInButton {
				val mGoogleSignInClient =
					GoogleSignIn.getClient(this, viewModel.googleSignInOptions)
				startForResult.launch(mGoogleSignInClient.signInIntent)
			}
			CustomTheme {
				Text(text = counterState.orEmpty(), color = MaterialTheme.colors.onSurface)
			}
		}
	}

	private val startForResult = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		viewModel.checkAccess(result)
	}
}