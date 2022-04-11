package com.shov.filesfeature.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.ui.texts.CustomText
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.DownloadViewModel

@Composable
fun CustomBottomAppBar(//TODO Remove
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel()
) {
	Box(
		modifier = Modifier
			.background(color = MaterialTheme.colorScheme.primary)
			.navigationBarsPadding()
	) {
		BottomAppBar(
			containerColor = MaterialTheme.colorScheme.primary,
			contentColor = MaterialTheme.colorScheme.onPrimary,
		) {
			Box(modifier = Modifier.padding(4.dp)) {
				if (downloadViewModel.title.isNotEmpty()) {
					CircularProgressIndicator(
						color = LocalContentColor.current,
						modifier = Modifier.align(Alignment.Center)
					)
				}

				AnimatedIconButton(
					modifier = Modifier.align(Alignment.CenterStart),
					enabled = true,
					imageVector = Icons.Rounded.Download,
					onClick = {
						scaffold.showSnackbar("List with downloads unavailable now")
					}
				)
			}

			if (downloadViewModel.title.isNotEmpty()) {
				CustomText(
					modifier = Modifier
						.weight(1f)
						.padding(start = 8.dp),
					text = stringResource(id = R.string.downloading, downloadViewModel.title)
				)
			}
		}
	}
}
