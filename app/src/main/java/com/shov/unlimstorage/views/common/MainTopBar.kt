package com.shov.unlimstorage.views.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shov.coreui.ui.CustomTopAppBar
import com.shov.coreui.viewModels.singletonViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel

@Composable
fun MainTopBar(topAppBarViewModel: TopAppBarViewModel = singletonViewModel()) {
	CustomTopAppBar(
		prevRouteImageVector = topAppBarViewModel.topAppBar.prevRoute?.first,
		onPrevRouteClick = topAppBarViewModel.topAppBar.prevRoute?.second ?: {},
		prevRouteEnabled = topAppBarViewModel.topAppBar.prevRoute != null,
		title = topAppBarViewModel.topAppBar.title,
		nextRouteImageVector = topAppBarViewModel.topAppBar.nextRoute?.first,
		onNextRouteClick = topAppBarViewModel.topAppBar.nextRoute?.second ?: {},
		nextRouteEnabled = topAppBarViewModel.topAppBar.nextRoute != null
	)

	LaunchedEffect(key1 = topAppBarViewModel.topAppBar.prevRoute) {
		topAppBarViewModel.onPrevRouteChange()
	}
	LaunchedEffect(key1 = topAppBarViewModel.topAppBar.nextRoute) {
		topAppBarViewModel.onNextRouteChange()
	}
}
