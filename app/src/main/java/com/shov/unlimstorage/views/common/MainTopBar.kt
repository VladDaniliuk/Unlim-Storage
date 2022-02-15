package com.shov.unlimstorage.views.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shov.unlimstorage.ui.CustomTopAppBar
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun MainTopBar(topAppBarViewModel: TopAppBarViewModel = singletonViewModel()) {
	CustomTopAppBar(
		prevRouteImageVector = topAppBarViewModel.topAppBar.prevRoute?.first,
		onPrevRouteClick = topAppBarViewModel.topAppBar.prevRoute?.second ?: {},
		prevRouteVisible = topAppBarViewModel.topAppBar.prevRoute != null,
		title = topAppBarViewModel.topAppBar.title,
		nextRouteImageVector = topAppBarViewModel.topAppBar.nextRoute?.first,
		onNextRouteClick = topAppBarViewModel.topAppBar.nextRoute?.second ?: {},
		nextRouteVisible = topAppBarViewModel.topAppBar.nextRoute != null
	)

	LaunchedEffect(key1 = topAppBarViewModel.topAppBar.prevRoute) {
		topAppBarViewModel.onPrevRouteChange()
	}
	LaunchedEffect(key1 = topAppBarViewModel.topAppBar.nextRoute) {
		topAppBarViewModel.onNextRouteChange()
	}
}
