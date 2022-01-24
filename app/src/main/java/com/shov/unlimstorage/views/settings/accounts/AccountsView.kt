package com.shov.unlimstorage.views.settings.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.AccountMenuLink

@Composable
fun AccountsView(
	accountsAccessList: List<StorageType>,
	onAccountClick: (StorageType) -> Unit,
	isCanAddAccount: Boolean,
	onAddNewAccountClick: () -> Unit
) {
	Column {
		accountsAccessList.forEach { storageType ->
			AccountMenuLink(
				accountId = storageType.nameId,
				imageId = storageType.imageId,
				subtitleId = R.string.click_to_delete,
				titleId = R.string.account,
				onClick = { onAccountClick(storageType) }
			)
		}

		Divider()

		if (isCanAddAccount) {
			SettingsMenuLink(
				icon = {
					Icon(
						imageVector = Icons.Rounded.Add,
						contentDescription = Icons.Rounded.Add.name
					)
				},
				title = { Text(text = stringResource(R.string.add_other_account)) },
				onClick = onAddNewAccountClick
			)
		}
	}
}

@Preview(showSystemUi = true)
@Composable
fun AccountFragmentPreview() {
	AccountsView(
		accountsAccessList = StorageType.values().toList(),
		onAccountClick = {},
		isCanAddAccount = true
	) {}
}
