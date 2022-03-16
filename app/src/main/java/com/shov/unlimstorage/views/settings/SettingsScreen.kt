package com.shov.unlimstorage.views.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun SettingsScreen(
	context: Context = LocalContext.current,
	navigateTo: (String) -> Unit,
	onBackClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
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
	Column {
		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.AccountCircle)
			},
			subtitle = stringResource(R.string.accounts_description),
			title = stringResource(R.string.accounts)
		) {
			onNavigate(Screen.Accounts.route)
		}

		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.Security)
			},
			subtitle = stringResource(R.string.use_pin_code),
			title = stringResource(R.string.security)
		) {
			onNavigate(Screen.Security.route)
		}

		MenuLink(
			icon = {
				CustomIcon(imageVector = Icons.Rounded.Update)
			},
			subtitle = stringResource(R.string.check_update_disable_auto),
			title = stringResource(R.string.about_updates)
		) {
			onNavigate(Screen.Updates.route)
		}
	}
}

@Preview
@Composable
private fun SettingsScreenPreview() {
	SettingsView {}
}
