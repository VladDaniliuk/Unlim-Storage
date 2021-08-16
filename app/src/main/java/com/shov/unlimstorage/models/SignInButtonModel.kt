package com.shov.unlimstorage.models

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.annotation.StringRes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.viewModels.SignInViewModel

data class SignInButtonInfo(
	@StringRes val buttonId: Int,
	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		storageType: StorageType
	) -> Unit,
	val checkAccess: (result: ActivityResult, storageType: StorageType) -> Unit,
	val storageType: StorageType
)

fun getSignInButtons(signInViewModel: SignInViewModel): List<SignInButtonInfo> =
	listOf(
		SignInButtonInfo(
			R.string.google,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			StorageType.GOOGLE
		),
		SignInButtonInfo(
			R.string.box,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			StorageType.BOX
		),
		SignInButtonInfo(
			R.string.dropbox,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			StorageType.DROPBOX
		),
		SignInButtonInfo(
			R.string.onedrive,
			signInViewModel.getAccess,
			signInViewModel.checkAccess,
			StorageType.GOOGLE//ONEDRIVE
		)
	)
