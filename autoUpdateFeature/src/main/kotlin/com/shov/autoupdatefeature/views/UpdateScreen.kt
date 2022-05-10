package com.shov.autoupdatefeature.views

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.shov.autoupdatefeature.R
import com.shov.autoupdatefeature.viewModels.UpdateViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun UpdateScreen(
	context: Context = LocalContext.current,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)

	UpdateView(
		onCheckForUpdateClick = {
			if (isConnected) {
				updateViewModel.checkAppVersion(
					context.packageManager.getPackageInfo(context.packageName, 0).versionName
				) {
					scaffold.showSnackbar(context.getString(R.string.have_latest_version))
				}
			} else {
				scaffold.showSnackbar(context.getString(R.string.connection_failed))
			}
		},
		isShowAgain = updateViewModel.isShowAgain,
		isShowProgress = updateViewModel.isShowProgress,
		onAutoCheckClick = updateViewModel::setShowDialogAgain
	)

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to navigationViewModel::popBack,
			title = context.getString(R.string.updates)
		)
	}
}
