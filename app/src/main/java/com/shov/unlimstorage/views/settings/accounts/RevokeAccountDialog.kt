package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel

@Composable
fun RevokeAccountDialog(accountsViewModel: AccountsViewModel, storageType: StorageType) {
	AlertDialog(
		dismissButton = {
			TextButton(onClick = { accountsViewModel.showRevokeDialog() }) {
				Text(text = stringResource(id = android.R.string.cancel))
			}
		},
		confirmButton = {
			TextButton(onClick = {
				accountsViewModel.signOut(storageType)
				accountsViewModel.setIsAllSignedIn(false)
				accountsViewModel.showRevokeDialog()
			}) {
				Text(text = stringResource(id = R.string.revoke))
			}
		},
		onDismissRequest = { accountsViewModel.showRevokeDialog() },
		properties = DialogProperties(),
		text = {
			Text(
				text = stringResource(
					id = R.string.revoke_access_to,
					stringResource(id = storageType.nameId)
				)
			)
		},
		title = { Text(text = stringResource(id = R.string.revoke_access)) },
	)
}
