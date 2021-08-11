package com.shov.unlimstorage.ui

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.values.ACCOUNTS

@Composable
fun AccountMenuLink(
	accountId: Int,
	imageId: Int,
	subtitleId: Int? = null,
	titleId: Int,
	onClick: () -> Unit
) {
	SettingsMenuLink(
		icon = {
			Icon(
				painter = painterResource(id = imageId),
				contentDescription = ACCOUNTS
			)
		},
		title = {
			Text(
				text = stringResource(
					id = titleId,
					stringResource(id = accountId)
				)
			)
		},
		subtitle = {
			subtitleId?.let { id ->
				Text(text = stringResource(id = id))
			}
		},
		onClick = onClick
	)
}
