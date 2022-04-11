package com.shov.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
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
	scrollBehavior: TopAppBarScrollBehavior,
) {
	Box(
		modifier = Modifier.background(
			TopAppBarDefaults.centerAlignedTopAppBarColors()
				.containerColor(scrollBehavior.scrollFraction).value
		)
	) {
		CenterAlignedTopAppBar(
			modifier = Modifier.statusBarsPadding(),
			title = {
				AnimatedText(
					modifier = Modifier.fillMaxWidth(),
					text = title
				)
			},
			navigationIcon = {
				AnimatedIconButton(
					imageVector = prevRouteImageVector,
					onClick = onPrevRouteClick,
					enabled = prevRouteEnabled,
				)
			},
			actions = {
				AnimatedIconButton(
					imageVector = nextRouteImageVector,
					onClick = onNextRouteClick,
					enabled = nextRouteEnabled,
				)
			},
			scrollBehavior = scrollBehavior
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
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
		nextRouteEnabled = true,
		scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
	)
}
