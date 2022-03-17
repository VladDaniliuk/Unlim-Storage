package com.shov.signinfeature.views

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreui.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AccountsViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
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
		storageTypes = accountsViewModel.checkAllAccess(),
		onShowRevokeDialogClick = accountsViewModel::showRevokeDialog,
		isAddAccountAvailable = accountsViewModel.checkAllAccess(false).isNotEmpty(),
		accountsViewModel::showAddAccountBottomSheet
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			Icons.Rounded.ArrowBack to onBackClick,
			context.getString(R.string.accounts)
		)
	}
}