package com.shov.autoupdatefeature.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.shov.autoupdatefeature.utils.toPrettyTime
import com.shov.autoupdatefeature.viewModels.UpdateViewModel
import com.shov.coremodels.converters.toBytes
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun NewVersionDialog(
	context: Context = LocalContext.current,
	updateViewModel: UpdateViewModel = singletonViewModel(),
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

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
					updateViewModel.subscribeToDownload(
						lastRelease.assets[0].browserDownloadUrl,
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

	LaunchedEffect(key1 = isConnected) {
		if (
			updateViewModel.isShowAgain
				.and(isConnected)
				.and(updateViewModel.wasDialogShown.not())
		) {
			updateViewModel.checkAppVersion(
				context.packageManager.getPackageInfo(context.packageName, 0).versionName
			)
		}
	}
}
