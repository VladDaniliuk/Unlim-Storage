package com.shov.signinfeature.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.values.Dialog
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AccountsViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	AccountsView(
		storageTypes = accountsViewModel.storageTypes,
		navigateTo = navigationViewModel::navigateTo,
		isAddAccountAvailable = accountsViewModel.isAddAccountAvailable,
	)

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to navigationViewModel::popBack,
			context.getString(R.string.accounts)
		)
	}
}

@Composable
internal fun AccountsView(
	storageTypes: List<StorageType>,
	navigateTo: (String) -> Unit,
	isAddAccountAvailable: Boolean,
) {
	Column(modifier = Modifier.fillMaxSize()) {
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
				navigateTo(Dialog.RevokeAccount.setStorageType(storageType.name))
			}
		}

		Divider()

		if (isAddAccountAvailable) {
			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Add)
				},
				title = stringResource(R.string.add_other_account),
				onClick = {
					navigateTo(Dialog.AddAccount.route)
				}
			)
		}
	}
}

@Preview
@Composable
private fun AccountsPreview() {
	AccountsView(
		storageTypes = listOf(StorageType.BOX),
		navigateTo = {},
		isAddAccountAvailable = true
	)
}
