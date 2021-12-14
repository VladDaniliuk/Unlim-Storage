package com.shov.unlimstorage.views.settings.updates

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alorma.settings.composables.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.UpdateViewModel

@Composable
fun UpdateScreen(
	updateViewModel: UpdateViewModel,
	filesNavController: NavController,
	topAppBarViewModel: TopAppBarViewModel
) {
	topAppBarViewModel.setTopBar(
		Icons.Rounded.ArrowBack to { filesNavController.popBackStack() },
		stringResource(R.string.updates),
		null
	)

	Column {
		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Update,
					contentDescription = Icons.Rounded.Update.name
				)
			},
			title = { Text(text = stringResource(R.string.check_for_updates)) },
		) {
			updateViewModel.checkAppVersion()
		}

		SettingsMenuLink(
			icon = {
				Icon(
					imageVector = Icons.Rounded.Autorenew,
					contentDescription = Icons.Rounded.Autorenew.name
				)
			},
			title = { Text(text = stringResource(R.string.auto_check)) },
			action = {
				Switch(
					checked = updateViewModel.isShowAgain,
					onCheckedChange = { updateViewModel.setShowDialogAgain() }
				)
			}
		) {}
	}
}