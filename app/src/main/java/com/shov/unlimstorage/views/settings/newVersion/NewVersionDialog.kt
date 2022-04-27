package com.shov.unlimstorage.views.settings.newVersion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.provider.newVersionViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import com.shov.unlimstorage.views.permissions.PermissionDialog
import kotlinx.coroutines.flow.onEach

@Composable
fun NewVersionObserver(
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	updateViewModel: UpdateViewModel = singletonViewModel()
) {
	if (updateViewModel.isDialogShown) {
		NewVersionDialog(
			newVersionViewModel = newVersionViewModel(
				lastReleaseItem = updateViewModel.lastRelease!!
			)
		)
	}

	LaunchedEffect(key1 = null) {
		context.observeConnectivityAsFlow().onEach { isConnected ->
			if (updateViewModel.isShowAgain.and(isConnected)) updateViewModel.checkAppVersion()
		}.launchWhenStarted(lifecycleOwner.lifecycleScope)
	}
}

@Composable
fun NewVersionDialog(
	updateViewModel: UpdateViewModel = singletonViewModel(),
	newVersionViewModel: NewVersionViewModel,
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	context: Context = LocalContext.current
) {
	Dialog(onDismissRequest = updateViewModel::hideDialog) {
		NewVersionView(
			formatArgs = arrayOf(
				newVersionViewModel.lastReleaseItem.version,
				newVersionViewModel.lastReleaseItem.applicationName,
				newVersionViewModel.lastReleaseItem.releaseDate,
				newVersionViewModel.getSize(),
				newVersionViewModel.lastReleaseItem.releaseName
			),
			isChecked = updateViewModel.isShowAgain.not(),
			onCheckedChange = { updateViewModel.setShowDialogAgain() },
			onCancelRequest = updateViewModel::hideDialog,
			onCompleteRequest = newVersionViewModel::showCheckPermission
		)
	}

	if (newVersionViewModel.isCheckPermissionShow) {
		PermissionDialog(onDismissRequest = newVersionViewModel::showCheckPermission) {
			val name = context.getString(
				R.string.apk_name,
				newVersionViewModel.lastReleaseItem.version
			)

			downloadViewModel.subscribeToDownload(
				downloadViewModel.downloadNewVersion(
					name, newVersionViewModel.lastReleaseItem.downloadUrl
				), name
			)

			updateViewModel.hideDialog()
		}
	}

	DisposableEffect(key1 = null) {
		onDispose {
			newVersionViewModel.showCheckPermission(false)
		}
	}
}
