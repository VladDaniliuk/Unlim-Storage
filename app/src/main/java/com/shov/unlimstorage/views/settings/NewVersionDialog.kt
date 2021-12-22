package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Checkbox
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.CustomText
import com.shov.unlimstorage.ui.dialog.CustomHeaderDialog
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import com.shov.unlimstorage.views.Permission

@Composable
fun NewVersionDialog(
	updateViewModel: UpdateViewModel,
	newVersionViewModel: NewVersionViewModel,
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	onDismissRequest: () -> Unit
) {
	val context = LocalContext.current

	CustomHeaderDialog(
		onDismissRequest = onDismissRequest,
		header = stringResource(R.string.new_update),
		onCompleteText = stringResource(R.string.download).uppercase(),
		onCancelText = stringResource(android.R.string.cancel).uppercase(),
		onCancelRequest = onDismissRequest,
		onCompleteRequest = newVersionViewModel::showCheckPermission
	) {
		CustomText(
			text = stringResource(
				id = R.string.update_text,
				formatArgs = arrayOf(
					newVersionViewModel.lastReleaseItem.version,
					newVersionViewModel.lastReleaseItem.applicationName,
					newVersionViewModel.getReleaseDate(),
					newVersionViewModel.getSize(),
					newVersionViewModel.lastReleaseItem.releaseName
				)
			),
			textStyle = Typography().body1
		)

		Row(modifier = Modifier.paddingFromBaseline(36.dp)) {
			Checkbox(
				modifier = Modifier.padding(end = 8.dp),
				checked = updateViewModel.isShowAgain.not(),
				onCheckedChange = { updateViewModel.setShowDialogAgain() }
			)

			CustomText(
				modifier = Modifier.align(Alignment.CenterVertically),
				text = stringResource(R.string.dont_show_again),
				textStyle = Typography().body1
			)
		}
	}

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

				onDismissRequest()
			}
		)
	}

	DisposableEffect(key1 = null) {
		onDispose {
			newVersionViewModel.showCheckPermission(false)
		}
	}
}

@Preview
@Composable
fun NewVersionDialogPreview() {
	NewVersionDialog(
		updateViewModel = hiltViewModel(),
		newVersionViewModel = hiltViewModel(),
		onDismissRequest = {}
	)
}
