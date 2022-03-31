package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shov.autoupdatefeature.viewModels.UpdateViewModel
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.ui.texts.CustomText
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.DownloadViewModel

@Composable
fun CustomBottomAppBar(
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = singletonViewModel()
) {
	BottomAppBar(
		backgroundColor = MaterialTheme.colors.primary,
		contentColor = MaterialTheme.colors.onPrimary,
		contentPadding = WindowInsets.navigationBars.asPaddingValues(),
		cutoutShape = CircleShape
	) {
		Box(modifier = Modifier.padding(4.dp)) {
			CircularProgressIndicator(
				color = LocalContentColor.current,
				progress = downloadViewModel.percents,
				modifier = Modifier.align(Alignment.Center)
			)

			AnimatedIconButton(
				modifier = Modifier.align(Alignment.CenterStart),
				enabled = true,
				imageVector = if (downloadViewModel.percents != 0f) Icons.Rounded.Close else null,
				onClick = {
					updateViewModel.dismissDownloading()
					downloadViewModel.setProgress(0f, "")
				}
			)
		}

		if (downloadViewModel.percents != 0f) {
			CustomText(
				modifier = Modifier
					.weight(1f)
					.padding(start = 8.dp),
				text = stringResource(id = R.string.downloading, downloadViewModel.title)
			)
		}
	}
}