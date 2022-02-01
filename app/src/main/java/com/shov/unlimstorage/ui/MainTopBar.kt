package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BackHand
import androidx.compose.material.icons.rounded.NextPlan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.shov.unlimstorage.ui.buttons.animation.AnimatedIconButton
import com.shov.unlimstorage.ui.texts.animation.AnimatedText
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun MainTopBar(topAppBarViewModel: TopAppBarViewModel = singletonViewModel()) {
	MainTopBar(
		prevRouteImageVector = topAppBarViewModel.prevRouteOld?.first,
		onPrevRouteClick = topAppBarViewModel.prevRouteOld?.second ?: {},
		prevRouteVisible = topAppBarViewModel.prevRoute != null,
		title = topAppBarViewModel.title,
		nextRouteImageVector = topAppBarViewModel.nextRouteOld?.first,
		onNextRouteClick = topAppBarViewModel.nextRouteOld?.second ?: {},
		nextRouteVisible = topAppBarViewModel.nextRoute != null
	)

	LaunchedEffect(key1 = topAppBarViewModel.prevRoute) { topAppBarViewModel.onPrevRouteChange() }
	LaunchedEffect(key1 = topAppBarViewModel.nextRoute) { topAppBarViewModel.onNextRouteChange() }
}

@Composable
fun MainTopBar(
	prevRouteImageVector: ImageVector?,
	onPrevRouteClick: () -> Unit,
	prevRouteVisible: Boolean,
	title: String?,
	nextRouteImageVector: ImageVector?,
	onNextRouteClick: () -> Unit,
	nextRouteVisible: Boolean,
) {
	TopAppBar(
		contentPadding = rememberInsetsPaddingValues(
			applyBottom = false,
			insets = LocalWindowInsets.current.statusBars
		),
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(all = 4.dp)
		) {
			AnimatedIconButton(
				modifier = Modifier.align(Alignment.CenterStart),
				imageVector = prevRouteImageVector,
				onClick = onPrevRouteClick,
				visible = prevRouteVisible
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
				onClick = onNextRouteClick,
				visible = nextRouteVisible
			)
		}
	}
}

@Preview
@Composable
fun MainTopBarPreview() {
	MainTopBar(
		prevRouteImageVector = Icons.Rounded.BackHand,
		onPrevRouteClick = {},
		prevRouteVisible = true,
		title = "Title",
		nextRouteImageVector = Icons.Rounded.NextPlan,
		onNextRouteClick = {},
		nextRouteVisible = true
	)
}
