package com.shov.unlimstorage.views.settings.accounts

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	accountsViewModel.showRevokeDialog?.let { storageType ->
		RevokeAccountDialog(
			nameId = storageType.nameId,
			onDismiss = accountsViewModel::showRevokeDialog
		) {
			accountsViewModel.signOut(storageType)
		}
	}

	if (accountsViewModel.isShowAddAccountBottomSheet) {
		AddAccountDialog()
	}

	Column {
		accountsViewModel.checkAllAccess().forEach { storageType ->
			AccountMenuLink(
				accountId = storageType.nameId,
				imageId = storageType.imageId,
				subtitleId = R.string.click_to_delete,
				titleId = R.string.account
			) {
				accountsViewModel.showRevokeDialog(storageType)
			}
		}

		Divider()

		if (accountsViewModel.checkAllAccess(false).isNotEmpty()) {
			SettingsMenuLink(
				icon = {
					Icon(
						imageVector = Icons.Rounded.Add,
						contentDescription = Icons.Rounded.Add.name
					)
				},
				title = { Text(text = stringResource(R.string.add_other_account)) }
			) {
				accountsViewModel.showAddAccountBottomSheet(true)
			}
		}
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to onBackClick,
			context.getString(R.string.accounts)
		)
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
					contentDescription = Icons.Rounded.Add.name
				)
			},
			title = { Text(text = stringResource(R.string.add_other_account)) }
		) {}
	}
}
