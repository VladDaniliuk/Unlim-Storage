package com.shov.signinfeature.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AccountsViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	navigateToRevokeAccountDialog: (StorageType) -> Unit,
	navigateToAddAccountDialog: () -> Unit
) {
	AccountsView(
		storageTypes = accountsViewModel.storageTypes,
		onShowRevokeDialogClick = navigateToRevokeAccountDialog,
		isAddAccountAvailable = accountsViewModel.isAddAccountAvailable,
		onShowAddAccountDialogClick = navigateToAddAccountDialog
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			Icons.Rounded.ArrowBack to onBackClick,
			context.getString(R.string.accounts)
		)
	}
}

@Composable
internal fun AccountsView(
	storageTypes: List<StorageType>,
	onShowRevokeDialogClick: (StorageType) -> Unit,
	isAddAccountAvailable: Boolean,
	onShowAddAccountDialogClick: () -> Unit
) {
	Column {
		storageTypes.forEach { storageType: StorageType ->
			MenuLink(
				icon = {
					CustomIcon(
						painter = painterResource(storageType.imageId),
						tint = Color.Unspecified
					)
				},
				title = stringResource(R.string.account, stringResource(storageType.nameId)),
				subtitle = stringResource(R.string.click_to_delete)
			) {
				onShowRevokeDialogClick(storageType)
			}
		}

		Divider()

		if (isAddAccountAvailable) {
			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Add)
				},
				title = stringResource(R.string.add_other_account),
				onClick = onShowAddAccountDialogClick
			)
		}
	}
}

@Preview
@Composable
private fun AccountsPreview() {
	AccountsView(
		storageTypes = listOf(StorageType.BOX),
		onShowRevokeDialogClick = {},
		isAddAccountAvailable = true
	) {}
}
