package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.ui.Dialog
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.viewModels.SignInViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AccountsFragment(signInViewModel: SignInViewModel) {
	val bottomSheetState =
		rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
	val scope = rememberCoroutineScope()
	val showDialog by signInViewModel.showDialog.collectAsState()

	if (showDialog.first) {
		Dialog(
			addableText = showDialog.second?.buttonId,
			text = R.string.revoke_access_to,
			title = R.string.revoke_access,
			onDismissRequest = { signInViewModel.setShowDialog(false, null) },
			onDismissButtonClick = { signInViewModel.setShowDialog(false, null) },
			dismissButtonText = android.R.string.cancel,
			onConfirmButtonClick = {
				showDialog.second?.let { signInButtonInfo ->
					signInViewModel.signOut(signInButtonInfo.signInType)
					signInViewModel.setShowDialog(false, null)
				}
			},
			confirmButtonText = R.string.revoke
		)
	}

	Column {
		getSignInButtons(signInViewModel = signInViewModel).forEach { signInButtonInfo ->
			if (signInViewModel.checkAccess(signInType = signInButtonInfo.signInType)) {
				AccountMenuLink(
					accountId = signInButtonInfo.buttonId,
					imageId = signInButtonInfo.image,
					subtitleId = R.string.click_to_delete,
					titleId = R.string.account
				) {
					signInViewModel.setShowDialog(true, signInButtonInfo)
				}
			}
		}

		Divider()

		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = ACCOUNTS
				)
			},
			title = { Text(text = stringResource(R.string.add_other_account)) }
		) {
			scope.launch { bottomSheetState.show() }
		}
	}

	AddAccountBottomSheet(bottomSheetState, hiltViewModel())
}

@Preview
@ExperimentalMaterialApi
@Composable
fun AccountFragmentPreview() {
	AccountsFragment(hiltViewModel())
}
