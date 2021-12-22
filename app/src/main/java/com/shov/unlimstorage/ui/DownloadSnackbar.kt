package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R

@Composable
fun DownloadSnackbar(
	modifier: Modifier = Modifier,
	title: String,
	backgroundColor: Color = MaterialTheme.colors.background,
	percents: Float = 0f,
	onDismissRequest: () -> Unit = {}
) {
	Card(
		shape = MaterialTheme.shapes.small.copy(CornerSize(0)),
		elevation = 16.dp,
		backgroundColor = backgroundColor,
		modifier = modifier.fillMaxWidth()
	) {
		Row(modifier = Modifier.navigationBarsPadding()) {
			Text(
				modifier = Modifier
					.align(Alignment.CenterVertically)
					.weight(1f)
					.padding(start = 8.dp),
				text = stringResource(
					id = R.string.downloading,
					title
				)
			)

			Box(
				modifier = Modifier
					.padding(end = 8.dp)
					.padding(vertical = 4.dp)
			) {
				CircularProgressIndicator(
					progress = percents,
					modifier = Modifier.align(Alignment.Center)
				)

				IconButton(
					onClick = onDismissRequest,
					modifier = Modifier.align(Alignment.Center)
				) {
					Icon(
						imageVector = Icons.Rounded.Close,
						contentDescription = Icons.Rounded.Close.name
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun DownloadSnackbarPreview() {
	DownloadSnackbar(title = "r.txt")
}
