package com.shov.autoupdatefeature.views

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.shov.autoupdatefeature.R
import com.shov.autoupdatefeature.viewModels.UpdateViewModel
import com.shov.coreui.ui.LocalHostState
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UpdateScreen(
	context: Context = LocalContext.current,
	coroutine: CoroutineScope = rememberCoroutineScope(),
	navigationViewModel: NavigationViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel(),
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)

	CustomScaffold(
		title = {
			Text(text = stringResource(id = R.string.updates))
		},
		navigationIcon = {
			IconButton(onClick = navigationViewModel::popBack) {
				Icon(
					imageVector = Icons.Rounded.ArrowBack,
					contentDescription = null
				)
			}
		}
	) { paddingValues ->
		val snackbar: SnackbarHostState = LocalHostState.current

		UpdateView(
			onCheckForUpdateClick = {
				if (isConnected) {
					updateViewModel.checkAppVersion(
						context.packageManager.getPackageInfo(context.packageName, 0).versionName
					) {
						coroutine.launch {
							snackbar.showSnackbar(context.getString(R.string.have_latest_version))
						}
					}
				} else {
					coroutine.launch {
						snackbar.showSnackbar(context.getString(R.string.connection_failed))
					}
				}
			},
			isShowAgain = updateViewModel.isShowAgain,
			isShowProgress = updateViewModel.isShowProgress,
			onAutoCheckClick = updateViewModel::setShowDialogAgain,
			paddingValues = paddingValues
		)
	}
}
