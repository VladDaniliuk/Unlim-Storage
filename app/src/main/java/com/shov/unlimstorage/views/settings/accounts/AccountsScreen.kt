package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.viewModels.AccountsViewModel
import com.shov.unlimstorage.viewModels.TopAppBarViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel,
	filesNavController: NavController,
	topAppBarViewModel: TopAppBarViewModel
) {
	topAppBarViewModel.setTopBar(
		Icons.Rounded.ArrowBack to { filesNavController.popBackStack() },
		stringResource(id = R.string.accounts),
		null
	)

	accountsViewModel.showRevokeDialog?.let { storageType ->
		RevokeAccountDialog(
			accountsViewModel = hiltViewModel(),
			storageType = storageType
		)
	}

	accountsViewModel.showAddAccountBottomSheet?.let {
		AddAccountDialog(
			accountsViewModel = hiltViewModel(),
			signInViewModel = hiltViewModel()
		)
	}

	Column {
		StorageType.values().forEach { storageType ->
			if (accountsViewModel.checkAccess(storageType)) {
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

		if (accountsViewModel.isAllSignedIn.not()) {
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

@Preview(showSystemUi = true)
@Composable
fun AccountFragmentPreview() {
	Column {
		StorageType.values().forEach { storageType ->
			AccountMenuLink(
				accountId = storageType.nameId,
				imageId = storageType.imageId,
				subtitleId = R.string.click_to_delete,
				titleId = R.string.account
			) {}
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
		) {}
	}
}
