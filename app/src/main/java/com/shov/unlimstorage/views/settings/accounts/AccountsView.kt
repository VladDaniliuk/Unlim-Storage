package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.unlimstorage.R

@Composable
internal fun AccountsView(
	storageTypes: List<StorageType>,
	onShowRevokeDialogClick: (StorageType) -> Unit,
	isAddAccountAvailable: Boolean,
	onShowAddAccountDialogClick: (Boolean) -> Unit
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
				title = stringResource(R.string.add_other_account)
			) {
				onShowAddAccountDialogClick(true)
			}
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
