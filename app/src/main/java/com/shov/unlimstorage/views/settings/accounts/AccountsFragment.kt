package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
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
import com.shov.unlimstorage.models.getSignInButtons
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.viewModels.AccountsViewModel

@ExperimentalMaterialApi
@Composable
fun AccountsFragment(accountsViewModel: AccountsViewModel) {
	val isAllSignedIn by accountsViewModel.isAllSignedIn.collectAsState()
	val showRevokeDialog by accountsViewModel.showRevokeDialog.collectAsState()
	val showAddAccountBottomSheet by accountsViewModel.showAddAccountBottomSheet.collectAsState()

	showRevokeDialog?.let { signInButtonInfo ->
		RevokeAccountAccess(
			accountsViewModel = hiltViewModel(),
			signInButtonInfo = signInButtonInfo
		)
	}

	showAddAccountBottomSheet?.let { AddAccountDialog(accountsViewModel = hiltViewModel()) }

	Column {
		getSignInButtons(hiltViewModel()).forEach { signInButtonInfo ->
			if (accountsViewModel.checkAccess(signInType = signInButtonInfo.signInType)) {
				AccountMenuLink(
					accountId = signInButtonInfo.buttonId,
					imageId = signInButtonInfo.image,
					subtitleId = R.string.click_to_delete,
					titleId = R.string.account
				) {
					accountsViewModel.setShowRevokeDialog(signInButtonInfo)
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
@ExperimentalMaterialApi
@Composable
fun AccountFragmentPreview() {
	AccountsFragment(hiltViewModel())
}
