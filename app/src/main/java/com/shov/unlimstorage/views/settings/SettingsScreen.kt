package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.TopAppBarViewModel

@Composable
fun SettingsScreen(
	filesNavController: NavController,
	topAppBarViewModel: TopAppBarViewModel
) {
	topAppBarViewModel.setTopBar(
		Icons.Rounded.ArrowBack to { filesNavController.popBackStack() },
		stringResource(R.string.settings),
		null
	)

	Column {
		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.AccountCircle,
					contentDescription = ACCOUNTS
				)
			},
			subtitle = { Text(text = stringResource(R.string.accounts_description)) },
			title = { Text(text = stringResource(R.string.accounts)) }
		) {
			filesNavController.navigate(Screen.Accounts.route)
		}

		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Update,
					contentDescription = Icons.Rounded.Update.name
				)
			},
			title = { Text(text = stringResource(R.string.about_updates)) },
			subtitle = { Text(text = stringResource(R.string.check_update_disable_auto)) }
		) {
			filesNavController.navigate(Screen.Updates.route)
		}
	}
}

@Preview(showSystemUi = true)
@Composable
fun SettingsFragmentPreview() {
	SettingsScreen(
		filesNavController = rememberNavController(),
		topAppBarViewModel = hiltViewModel()
	)
}
