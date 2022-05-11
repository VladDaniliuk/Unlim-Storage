package com.shov.settingsfeature.views.theme

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.models.Theme
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.settingsfeature.R

@Composable
fun ThemeView(
	theme: Theme,
	expandMenu: (Boolean) -> Unit,
	isExpanded: Boolean,
	changeTheme: (Theme) -> Unit,
	isDynamicTheme: Boolean,
	changeDynamicTheme: (Boolean) -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxHeight()
			.verticalScroll(rememberScrollState())
	) {
		Box {
			MenuLink(
				icon = {
					CustomIcon(imageVector = Icons.Rounded.DarkMode)
				},
				title = stringResource(R.string.theme),
				subtitle = theme.name
			) {
				expandMenu(true)
			}

			ThemeDropdownMenu(
				isExpanded = isExpanded,
				expandMenu = expandMenu,
				changeTheme = changeTheme
			)
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			MenuLink(icon = {
				CustomIcon(imageVector = Icons.Rounded.Palette)
			},
				title = stringResource(R.string.dynamic_colors),
				subtitle = stringResource(R.string.dynamic_colors_description),
				action = {
					Switch(
						checked = isDynamicTheme,
						onCheckedChange = changeDynamicTheme
					)
				}
			) {
				changeDynamicTheme(isDynamicTheme.not())
			}
		}
	}
}

@Preview
@Composable
fun ThemePreview() {
	ThemeView(
		theme = Theme.Light,
		expandMenu = {},
		isExpanded = false,
		changeTheme = {},
		isDynamicTheme = true,
		changeDynamicTheme = {}
	)
}
