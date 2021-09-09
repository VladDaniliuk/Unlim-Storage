package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.viewModels.AccountsViewModel

@Composable
fun AccountsFragment(accountsViewModel: AccountsViewModel) {
	val isAllSignedIn by accountsViewModel.isAllSignedIn.collectAsState()
	val showRevokeDialog by accountsViewModel.showRevokeDialog.collectAsState()
	val showAddAccountBottomSheet by accountsViewModel.showAddAccountBottomSheet.collectAsState()

	showRevokeDialog?.let { storageType ->
		RevokeAccountAccess(
			accountsViewModel = hiltViewModel(),
			storageType = storageType
		)
	}

	showAddAccountBottomSheet?.let {
		AddAccountDialog(
			accountsViewModel = hiltViewModel(),
			signInViewModel = hiltViewModel()
		)
	}

	Column {
		StorageType.values().forEach { storageType ->
			if (accountsViewModel.checkAccess(storageType = storageType)) {
				AccountMenuLink(
					accountId = storageType.nameId,
					imageId = storageType.imageId,
					subtitleId = R.string.click_to_delete,
					titleId = R.string.account
				) {
					accountsViewModel.setShowRevokeDialog(storageType)
				}
			} else accountsViewModel.setAllSignedIn(false)
		}

		Divider()

		if (isAllSignedIn.not()) {
			SettingsMenuLink(
				icon = {
					Icon(
						imageVector = Icons.Rounded.Add,
						contentDescription = ACCOUNTS
					)
				},
				title = { Text(text = stringResource(R.string.add_other_account)) }
			) {
				accountsViewModel.setShowAddAccountBottomSheet(true)
			}
		}
	}
}

@Preview
@Composable
fun AccountFragmentPreview() {
	AccountsFragment(hiltViewModel())
}
