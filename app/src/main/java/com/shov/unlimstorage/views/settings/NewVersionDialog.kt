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
import com.shov.unlimstorage.utils.converters.toPrettyTime
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
					lastRelease.tagName,
					lastRelease.assets[0].name,
					lastRelease.publishedAd.toPrettyTime(),
					lastRelease.assets[0].size.toBytes().let { size ->
						context.getString(size.second, size.first)
					},
					lastRelease.name
				),
				onCheckBoxCheckedChange = updateViewModel::setShowDialogAgain,
				onConfirmButtonClick = {
					downloadViewModel.subscribeToDownload(
						lastRelease.assets[0].browserDownloadedUrl,
						lastRelease.assets[0].name,
						lastRelease.tagName
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
