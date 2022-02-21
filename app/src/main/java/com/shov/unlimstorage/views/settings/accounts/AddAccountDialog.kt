package com.shov.unlimstorage.views.settings.accounts

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.CheckDropboxCredential
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.ui.CustomDialogContent
import com.shov.unlimstorage.ui.customHeaderText
import com.shov.unlimstorage.viewModels.SignInViewModel
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel

@Composable
fun AddAccountDialog(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	signInViewModel: SignInViewModel = hiltViewModel(),
) {
	Dialog(onDismissRequest = { accountsViewModel.showAddAccountBottomSheet() }) {
		CustomDialogContent(header = { customHeaderText(stringResource(R.string.choose_drive)) }) {
			accountsViewModel.checkAllAccess(false).forEach { storageType ->
				val startForResult = rememberLauncherForActivityResult(
					ActivityResultContracts.StartActivityForResult()
				) { result ->
					signInViewModel.checkAccessWithResult(result, storageType)
					accountsViewModel.showAddAccountBottomSheet()
				}

				AccountMenuLink(
					accountId = storageType.nameId,
					imageId = storageType.imageId,
					titleId = R.string.add_account
				) {
					signInViewModel.getAccess(startForResult, storageType)
				}
			}
		}
	}

	CheckDropboxCredential(
		additionalCheck = accountsViewModel.checkAccess(StorageType.DROPBOX).not(),
		onGetCredential = accountsViewModel::showAddAccountBottomSheet
	)
}
