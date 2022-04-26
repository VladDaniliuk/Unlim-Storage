package com.shov.settingsfeature.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R

@Composable
fun SettingsScreen(
	context: Context = LocalContext.current,
	navigateTo: (String) -> Unit,
	onBackClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel()
) {
	SettingsView(onNavigate = navigateTo)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to onBackClick,
			title = context.getString(R.string.settings)
		)
	}
}

@Composable
internal fun SettingsView(onNavigate: (String) -> Unit) {
	Column(Modifier.verticalScroll(rememberScrollState())) {
		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.AccountCircle)
			},
			title = stringResource(R.string.accounts),
			subtitle = stringResource(R.string.accounts_description)
		) {
			onNavigate(Screen.Accounts.route)
		}

		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.Security)
			},
			title = stringResource(R.string.security),
			subtitle = stringResource(R.string.use_pin_code)
		) {
			onNavigate(Screen.Security.route)
		}

		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.Update)
			},
			title = stringResource(R.string.about_updates),
			subtitle = stringResource(R.string.check_update_disable_auto)
		) {
			onNavigate(Screen.Updates.route)
		}

		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.Palette)
			},
			title = stringResource(R.string.theme_settings),
			subtitle = stringResource(R.string.configure_theme)
		) {
			onNavigate(Screen.Theme.route)
		}
	}
}

@Preview
@Composable
private fun SettingsScreenPreview() {
	SettingsView {}
}
