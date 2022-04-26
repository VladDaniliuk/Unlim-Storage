package com.shov.settingsfeature.views.theme

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.shov.coreui.models.Theme
import com.shov.settingsfeature.R

@Composable
fun ThemeDropdownMenu(
	isExpanded: Boolean,
	expandMenu: (Boolean) -> Unit,
	changeTheme: (Theme) -> Unit
) {
	DropdownMenu(
		expanded = isExpanded,
		offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp, 0.dp),
		onDismissRequest = { expandMenu(false) }
	) {
		DropdownMenuItem(
			text = {
				Text(text = stringResource(R.string.light))
			},
			onClick = {
				changeTheme(Theme.Light)
				expandMenu(false)
			}
		)

		DropdownMenuItem(
			text = {
				Text(text = stringResource(R.string.dark))
			},
			onClick = {
				changeTheme(Theme.Dark)
				expandMenu(false)
			}
		)

		DropdownMenuItem(
			text = {
				Text(text = stringResource(R.string.system))
			},
			onClick = {
				changeTheme(Theme.System)
				expandMenu(false)
			}
		)
	}
}

@Preview
@Composable
fun ThemeDropdownPreview() {
	ThemeDropdownMenu(
		isExpanded = true,
		expandMenu = {},
		changeTheme = {}
	)
}