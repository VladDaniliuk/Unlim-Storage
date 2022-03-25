package com.shov.unlimstorage.views.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.shov.autoupdatefeature.views.NewVersionView
import com.shov.coremodels.converters.toBytes
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import kotlinx.coroutines.flow.onEach

@Composable
fun NewVersionDialog(
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel()
) {
	if (updateViewModel.isDialogShown) {
		updateViewModel.lastRelease?.let { lastRelease ->
			NewVersionView(
				checkBoxChecked = updateViewModel.isShowAgain.not(),
				lastReleaseFormatArgs = arrayOf(
					lastRelease.version,
					lastRelease.applicationName,
					lastRelease.releaseDate,
					lastRelease.applicationSize.toBytes().let { size ->
						context.getString(size.second, size.first)
					},
					lastRelease.releaseName
				),
				onCheckBoxCheckedChange = updateViewModel::setShowDialogAgain,
				onConfirmButtonClick = {
					downloadViewModel.subscribeToDownload(
						lastRelease.downloadUrl,
						lastRelease.applicationName,
						lastRelease.version
					)

					updateViewModel.hideDialog()
				},
				onDismissButtonClick = updateViewModel::hideDialog,
				onDismissRequest = updateViewModel::hideDialog,
			)
		}
	}

	LaunchedEffect(key1 = null) {
		context.observeConnectivityAsFlow().onEach { isConnected ->
			if (
				updateViewModel.isShowAgain
					.and(isConnected)
					.and(updateViewModel.wasDialogShown.not())
			) {
				updateViewModel.checkAppVersion()
			}
		}.launchWhenStarted(lifecycleOwner.lifecycleScope)
	}
}
