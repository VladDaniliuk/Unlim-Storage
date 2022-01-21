package com.shov.unlimstorage.views.settings.updates

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel

@Composable
fun UpdateScreen(
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel(),
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel()
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)

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
			if (isConnected) {
				updateViewModel.checkAppVersion()
			} else {
				scaffoldViewModel.showSnackbar(context.getString(R.string.connection_failed))
			}
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

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to onBackClick,
			context.getString(R.string.updates),
			null
		)
	}
}
