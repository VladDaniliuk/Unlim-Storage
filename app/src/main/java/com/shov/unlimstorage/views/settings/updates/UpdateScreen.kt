package com.shov.unlimstorage.views.settings.updates

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Switch
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
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.menuLinks.MenuLink
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.unlimstorage.R
import com.shov.coreui.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel

@Composable
fun UpdateScreen(
	context: Context = LocalContext.current,
	onBackClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel(),
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)

	UpdateView(
		onCheckForUpdateClick = {
			if (isConnected) {
				updateViewModel.checkAppVersion()
			} else {
				scaffold.showSnackbar(context.getString(R.string.connection_failed))
			}
		},
		isShowAgain = updateViewModel.isShowAgain,
		onAutoCheckClick = updateViewModel::setShowDialogAgain
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to onBackClick,
			title = context.getString(R.string.updates)
		)
	}
}

@Composable
internal fun UpdateView(
	onCheckForUpdateClick: () -> Unit,
	isShowAgain: Boolean,
	onAutoCheckClick: () -> Unit
) {
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

@Preview
@Composable
private fun UpdatePreview() {
	UpdateView(
		onCheckForUpdateClick = {},
		isShowAgain = true
	) {}
}
