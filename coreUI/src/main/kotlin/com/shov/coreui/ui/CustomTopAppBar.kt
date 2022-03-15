package com.shov.coreui.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.ui.texts.AnimatedText

@Composable
fun CustomTopAppBar(
	prevRouteImageVector: ImageVector?,
	onPrevRouteClick: () -> Unit,
	prevRouteEnabled: Boolean,
	title: String?,
	nextRouteImageVector: ImageVector?,
	onNextRouteClick: () -> Unit,
	nextRouteEnabled: Boolean,
) {
	TopAppBar(
		backgroundColor = MaterialTheme.colors.primary,
		contentColor = MaterialTheme.colors.onPrimary,
		contentPadding = WindowInsets.statusBars.asPaddingValues()
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(all = 4.dp)
		) {
			AnimatedIconButton(
				modifier = Modifier.align(Alignment.CenterStart),
				imageVector = prevRouteImageVector,
				enabled = prevRouteEnabled,
				onClick = onPrevRouteClick
			)

			AnimatedText(
				modifier = Modifier
					.fillMaxWidth()
					.align(Alignment.Center)
					.padding(horizontal = 48.dp),
				text = title
			)

			AnimatedIconButton(
				modifier = Modifier.align(Alignment.CenterEnd),
				imageVector = nextRouteImageVector,
				enabled = nextRouteEnabled,
				onClick = onNextRouteClick
			)
		}
	}
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
	CustomTopAppBar(
		prevRouteImageVector = Icons.Rounded.ArrowBack,
		onPrevRouteClick = {},
		prevRouteEnabled = true,
		title = "Title",
		nextRouteImageVector = Icons.Rounded.ArrowBack,
		onNextRouteClick = {},
		nextRouteEnabled = true
	)
}
