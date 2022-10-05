package com.shov.coreui.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun LinearPullRefreshIndicator(
	swipeRefreshState: SwipeRefreshState,
	refreshTriggerDistance: Dp,
) {
	if (swipeRefreshState.isRefreshing) {
		LinearProgressIndicator(
			modifier = Modifier.fillMaxWidth(),
			backgroundColor = MaterialTheme.colors.background
		)
	} else {
		val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
		val progress = (swipeRefreshState.indicatorOffset / trigger).coerceIn(0f, 1f)
		LinearProgressIndicator(
			modifier = Modifier.fillMaxWidth(),
			backgroundColor = Color.Transparent,
			progress = progress
		)
	}
}