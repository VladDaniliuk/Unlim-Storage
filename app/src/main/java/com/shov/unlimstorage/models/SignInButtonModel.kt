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
	val signInType: SignInType,
	val image: Int
)

fun getSignInButtons(signInViewModel: SignInViewModel): List<SignInButtonInfo> =
	listOf(
		SignInButtonInfo(
			R.string.google,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.GOOGLE,
			R.drawable.ic_google_drive
		),
		SignInButtonInfo(
			R.string.box,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.BOX,
			R.drawable.ic_box
		),
		SignInButtonInfo(
			R.string.dropbox,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.DROPBOX,
			R.drawable.ic_drop_box
		),
		SignInButtonInfo(
			R.string.onedrive,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			SignInType.GOOGLE,//ONEDRIVE
			R.drawable.ic_one_drive
		)
	)
