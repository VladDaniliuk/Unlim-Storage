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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	accountsViewModel.showRevokeDialog?.let { storageType ->
		RevokeAccountDialog(
			nameId = storageType.nameId,
			onDismiss = accountsViewModel::showRevokeDialog
		) {
			coroutineScope.launch(Dispatchers.IO) {
				accountsViewModel.signOut(storageType)
				accountsViewModel.setIsAllSignedIn(false)
			}
		}
	}

	if (accountsViewModel.isShowAddAccountBottomSheet) {
		AddAccountDialog()
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
					accountsViewModel.showRevokeDialog(storageType)
				}
			} else accountsViewModel.setIsAllSignedIn(false)
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
					contentDescription = ACCOUNTS
				)
			},
			title = { Text(text = stringResource(R.string.add_other_account)) }
		) {}
	}
}
