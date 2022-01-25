package com.shov.unlimstorage.views.settings.accounts

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
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

	AccountsView(
		accountsAccessList = accountsViewModel.checkAllAccess(),
		onAccountClick = accountsViewModel::showRevokeDialog,
		isCanAddAccount = accountsViewModel.checkAllAccess(false).isNotEmpty()
	) {
		accountsViewModel.showAddAccountBottomSheet(true)
	}

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to onBackClick,
			context.getString(R.string.accounts)
		)
	}
}
