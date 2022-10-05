package com.shov.autoupdatefeature.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.autoupdatefeature.R
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink

@Composable
internal fun UpdateView(
	onCheckForUpdateClick: () -> Unit,
	isShowAgain: Boolean,
	isShowProgress: Boolean,
	onAutoCheckClick: () -> Unit
) {
	Box {
		if (isShowProgress) {
			LinearProgressIndicator(
				modifier = Modifier.fillMaxWidth(),
				backgroundColor = Color.Transparent
			)
		}

		Column {
			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Update)
				},
				title = stringResource(R.string.check_for_updates),
				onClick = onCheckForUpdateClick
			)

			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.Autorenew)
				},
				title = stringResource(R.string.auto_check),
				action = {
					Switch(
						checked = isShowAgain,
						onCheckedChange = { onAutoCheckClick() }
					)
				},
				onClick = onAutoCheckClick
			)
		}
	}
}

@Preview
@Composable
private fun UpdatePreview() {
	UpdateView(
		onCheckForUpdateClick = {},
		isShowAgain = true,
		isShowProgress = false
	) {}
}
