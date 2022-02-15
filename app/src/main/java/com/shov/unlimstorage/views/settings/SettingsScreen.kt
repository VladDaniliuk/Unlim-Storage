package com.shov.unlimstorage.views.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun SettingsScreen(
	context: Context = LocalContext.current,
	navigateTo: (String) -> Unit,
	onBackClick: () -> Unit,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	SettingsView(onNavigate = navigateTo)

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to onBackClick,
			title = context.getString(R.string.settings)
		)
	}
}

@Composable
fun SettingsView(onNavigate: (String) -> Unit) {
	Column {
		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.AccountCircle,
					contentDescription = Icons.Rounded.AccountCircle.name
				)
			},
			subtitle = { Text(text = stringResource(R.string.accounts_description)) },
			title = { Text(text = stringResource(R.string.accounts)) }
		) {
			onNavigate(Screen.Accounts.route)
		}

		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Update,
					contentDescription = Icons.Rounded.Update.name
				)
			},
			subtitle = { Text(text = stringResource(R.string.check_update_disable_auto)) },
			title = { Text(text = stringResource(R.string.about_updates)) }
		) {
			onNavigate(Screen.Updates.route)
		}
	}
}

@Preview
@Composable
fun SettingsScreenPreview() {
	CustomTheme {
		SettingsView {}
	}
}
