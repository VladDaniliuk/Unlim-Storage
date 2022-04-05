package com.shov.signinfeature.views

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.dialogs.CustomDialogContent
import com.shov.coreui.ui.dialogs.CustomHeaderText
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AccountsViewModel
import com.shov.signinfeature.viewModels.SignInViewModel

@Composable
fun AddAccountDialog(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	signInViewModel: SignInViewModel = hiltViewModel(),
) {
	Dialog(onDismissRequest = { accountsViewModel.showAddAccountBottomSheet() }) {
		AddAccountView(
			storageTypes = accountsViewModel.checkAllAccess(false),
			onResult = { result: ActivityResult, storageType: StorageType ->
				signInViewModel.checkAccessWithResult(result, storageType)
				accountsViewModel.showAddAccountBottomSheet()
			},
			intent = signInViewModel::getAccess
		)
	}
}

@Composable
internal fun AddAccountView(
	storageTypes: List<StorageType>,
	onResult: (result: ActivityResult, storageType: StorageType) -> Unit,
	intent: (storageType: StorageType) -> Intent
) {
	CustomDialogContent(header = { CustomHeaderText(stringResource(R.string.choose_drive)) }) {
		storageTypes.forEach { storageType: StorageType ->
			val startForResult = rememberLauncherForActivityResult(
				ActivityResultContracts.StartActivityForResult()
			) { result ->
				onResult(result, storageType)
			}

			MenuLink(
				icon = {
					CustomIcon(
						painter = painterResource(storageType.imageId),
						tint = Color.Unspecified
					)
				},
				title = stringResource(R.string.account, stringResource(storageType.nameId)),
				subtitle = stringResource(R.string.click_to_add),
			) {
				startForResult.launch(intent(storageType))
			}
		}
	}
}

@Preview
@Composable
private fun AddAccountPreview() {
	AddAccountView(
		storageTypes = StorageType.values().toList(),
		onResult = { _, _ -> },
		intent = { Intent() }
	)
}
