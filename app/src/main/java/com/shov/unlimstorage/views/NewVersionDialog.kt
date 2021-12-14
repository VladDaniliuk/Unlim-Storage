package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Checkbox
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.CustomDialog
import com.shov.unlimstorage.ui.CustomText
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.NewVersionViewModel
import com.shov.unlimstorage.viewModels.UpdateViewModel

@Composable
fun NewVersionDialog(
	updateViewModel: UpdateViewModel,
	newVersionViewModel: NewVersionViewModel,
	downloadViewModel: DownloadViewModel,
	onDismissRequest: () -> Unit
) {
	val context = LocalContext.current

	CustomDialog(
		onDismissRequest = onDismissRequest,
		header = stringResource(R.string.new_update),
		onCompleteText = stringResource(R.string.download).uppercase(),
		onCancelText = stringResource(android.R.string.cancel).uppercase(),
		onCancelRequest = onDismissRequest,
		onCompleteRequest = {
			val name = context.getString(
				R.string.apk_name,
				newVersionViewModel.lastRelease.version
			)

			downloadViewModel.subscribeToDownload(
				downloadViewModel.downloadNewVersion(
					name, newVersionViewModel.lastRelease.downloadUrl
				), name
			)

			onDismissRequest()
		}
	) {
		CustomText(
			text = stringResource(
				id = R.string.update_text,
				formatArgs = arrayOf(
					newVersionViewModel.lastRelease.version,
					newVersionViewModel.lastRelease.applicationName,
					newVersionViewModel.getReleaseDate(),
					newVersionViewModel.getSize(),
					newVersionViewModel.lastRelease.releaseName
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
				text = stringResource(R.string.dont_show_again),
				textStyle = Typography().body1
			)
		}
	}
}

@Preview
@Composable
fun NewVersionDialogPreview() {
	NewVersionDialog(
		updateViewModel = hiltViewModel(),
		newVersionViewModel = hiltViewModel(),
		downloadViewModel = hiltViewModel()
	) {}
}