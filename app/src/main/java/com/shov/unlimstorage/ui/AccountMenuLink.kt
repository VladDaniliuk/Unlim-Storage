package com.shov.unlimstorage.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink

@Composable
fun AccountMenuLink(
	@StringRes accountId: Int,
	imageId: Int,
	@StringRes subtitleId: Int? = null,
	@StringRes titleId: Int,
	onClick: () -> Unit
) {
	MenuLink(
		icon = {
			CustomIcon(
				painter = painterResource(imageId),
				tint = Color.Unspecified
			)
		},
		title = stringResource(titleId, stringResource(accountId)),
		subtitle = subtitleId?.let { id: Int ->
			stringResource(id)
		},
		onClick = onClick
	)
}

@Preview
@Composable
private fun AccountMenuLinkPreview() {
	AccountMenuLink(
		accountId = 0,
		imageId = StorageType.GOOGLE.imageId,
		titleId = StorageType.GOOGLE.nameId,
		subtitleId = StorageType.GOOGLE.nameId
	) {}
}

