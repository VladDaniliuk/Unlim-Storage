package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.values.ACCOUNTS
import com.shov.unlimstorage.values.navAccounts

@Composable
fun SettingsScreen(
	filesNavController: NavController,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		textId: Int?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit
) {
	setTopBar(
		Icons.Rounded.ArrowBack to { filesNavController.popBackStack() },
		R.string.settings,
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
			filesNavController.navigate(navAccounts)
		}
	}
}

@Preview(name = "Settings", showSystemUi = true)
@Composable
fun SettingsFragmentPreview() {
	SettingsScreen(
		filesNavController = rememberNavController(),
		setTopBar = { _, _, _ -> }
	)
}
