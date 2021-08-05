package com.shov.unlimstorage.models

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.annotation.StringRes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.signInModels.SignInType
import com.shov.unlimstorage.viewModels.SignInViewModel

data class SignInButtonInfo(
	@StringRes val buttonId: Int,
	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		signInType: SignInType
	) -> Unit,
	val checkAccess: (result: ActivityResult, signInType: SignInType) -> Unit,
	val signInType: SignInType
)

fun getSignInButtons(signInViewModel: SignInViewModel): List<SignInButtonInfo> =
	listOf(
		SignInButtonInfo(
			R.string.google,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.GOOGLE
		),
		SignInButtonInfo(
			R.string.box,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.GOOGLE//BOX
		),
		SignInButtonInfo(
			R.string.dropbox,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.DROPBOX
		),
		SignInButtonInfo(
			R.string.onedrive,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.GOOGLE//ONEDRIVE
		)
	)
