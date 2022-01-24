package com.shov.unlimstorage.ui

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R

@Composable
fun AccountMenuLink(
	@StringRes accountId: Int,
	imageId: Int,
	@StringRes subtitleId: Int? = null,
	@StringRes titleId: Int,
	onClick: () -> Unit
) {
	SettingsMenuLink(
		icon = {
			Icon(
				painter = painterResource(id = imageId),
				contentDescription = stringResource(
					id = titleId,
					stringResource(id = accountId)
				),
				tint = Color.Unspecified
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

@Preview
@Composable
fun AccountMenuLinkPreview() {
	AccountMenuLink(
		accountId = 0,
		imageId = R.drawable.ic_box,
		titleId = R.string.box,
		subtitleId = R.string.box
	) {}
}

