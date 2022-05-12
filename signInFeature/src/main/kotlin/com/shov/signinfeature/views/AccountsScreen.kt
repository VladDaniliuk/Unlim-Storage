package com.shov.signinfeature.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.values.Dialog
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.AccountsViewModel

@Composable
fun AccountsScreen(
	accountsViewModel: AccountsViewModel = hiltViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	CustomScaffold(
		title = {
			Text(stringResource(id = R.string.accounts))
		},
		navigationIcon = {
			IconButton(onClick = navigationViewModel::popBack) {
				Icon(
					imageVector = Icons.Rounded.ArrowBack,
					contentDescription = null
				)
			}
		}
	) { paddingValues ->
		AccountsView(
			paddingValues = paddingValues,
			storageTypes = accountsViewModel.storageTypes,
			navigateTo = navigationViewModel::navigateTo,
			isAddAccountAvailable = accountsViewModel.isAddAccountAvailable,
		)
	}
}

@Composable
internal fun AccountsView(
	paddingValues: PaddingValues,
	storageTypes: List<StorageType>,
	navigateTo: (String) -> Unit,
	isAddAccountAvailable: Boolean,
) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxHeight()
			.verticalScroll(rememberScrollState())
	) {
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
		isAddAccountAvailable = true,
		paddingValues = PaddingValues()
	)
}
