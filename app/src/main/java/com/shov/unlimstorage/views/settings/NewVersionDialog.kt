package com.shov.unlimstorage.views.settings

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Checkbox
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.shov.coremodels.converters.toBytes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.CustomDialogContent
import com.shov.unlimstorage.ui.customHeaderText
import com.shov.unlimstorage.ui.texts.CustomText
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.provider.newVersionViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import com.shov.unlimstorage.views.Permission
import kotlinx.coroutines.flow.onEach

@Composable
fun NewVersionDialog(
	context: Context = LocalContext.current,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	updateViewModel: UpdateViewModel = singletonViewModel()
) {
	if (updateViewModel.isDialogShown) {
		updateViewModel.lastRelease?.let { lastRelease ->
			NewVersionDialog(newVersionViewModel = newVersionViewModel(lastReleaseItem = lastRelease))
		}
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
	Dialog(
		content = {
			NewVersionDialogContent(
				formatArgs = arrayOf(
					newVersionViewModel.lastReleaseItem.version,
					newVersionViewModel.lastReleaseItem.applicationName,
					newVersionViewModel.lastReleaseItem.releaseDate,
					newVersionViewModel.lastReleaseItem.applicationSize.toBytes().let { size ->
						context.getString(size.second, size.first)
					},
					newVersionViewModel.lastReleaseItem.releaseName
				),
				isChecked = updateViewModel.isShowAgain.not(),
				onCheckedChange = { updateViewModel.setShowDialogAgain() },
				onCancelRequest = updateViewModel::hideDialog,
				onCompleteRequest = newVersionViewModel::showCheckPermission
			)
		}, onDismissRequest = updateViewModel::hideDialog
	)

	if (newVersionViewModel.isCheckPermissionShow) {
		Permission(
			onDismissRequest = newVersionViewModel::showCheckPermission,
			onHasAccess = {
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
		)
	}

	DisposableEffect(key1 = null) {
		onDispose {
			newVersionViewModel.showCheckPermission(false)
		}
	}
}

@Composable
fun NewVersionDialogContent(
	formatArgs: Array<String>,
	isChecked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	onCancelRequest: () -> Unit,
	onCompleteRequest: () -> Unit
) {
	CustomDialogContent(
		content = {
			CustomText(
				text = stringResource(
					id = R.string.update_text,
					formatArgs = formatArgs
				),
				textStyle = Typography().body1
			)

			Row(modifier = Modifier.paddingFromBaseline(36.dp)) {
				Checkbox(
					modifier = Modifier.padding(end = 8.dp),
					checked = isChecked,
					onCheckedChange = onCheckedChange
				)

				CustomText(
					modifier = Modifier.align(Alignment.CenterVertically),
					text = stringResource(R.string.dont_show_again),
					textStyle = Typography().body1
				)
			}
		},
		header = {
			customHeaderText(
				stringResource(
					R.string.new_,
					stringResource(R.string.update_available)
				)
			)
		},
		onCompleteText = stringResource(R.string.download).uppercase(),
		onCancelText = stringResource(android.R.string.cancel).uppercase(),
		onCancelRequest = onCancelRequest,
		onCompleteRequest = onCompleteRequest
	)
}

@Preview
@Composable
fun NewVersionDialogPreview() {
	NewVersionDialogContent(
		formatArgs = arrayOf("", "", "", "", ""),
		isChecked = false,
		onCheckedChange = {},
		onCancelRequest = {}
	) {}
}
