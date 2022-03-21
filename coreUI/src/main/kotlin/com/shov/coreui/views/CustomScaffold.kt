package com.shov.coreui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.shov.coreui.ui.CustomTopAppBar
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun CustomScaffold(
	scaffold: ScaffoldViewModel = singletonViewModel(),
	content: @Composable (PaddingValues) -> Unit
) {
	Scaffold(
		scaffoldState = scaffold.scaffoldState,
		snackbarHost = { hostState ->
			SnackbarHost(
				hostState = hostState,
				modifier = Modifier.navigationBarsPadding()
			) { snackBarData ->
				Snackbar(snackbarData = snackBarData)
			}
		},
		topBar = {
			CustomTopAppBar(
				prevRouteImageVector = scaffold.topAppBar.prevRoute?.first,
				onPrevRouteClick = scaffold.topAppBar.prevRoute?.second ?: {},
				prevRouteEnabled = scaffold.topAppBar.prevRoute != null,
				title = scaffold.topAppBar.title,
				nextRouteImageVector = scaffold.topAppBar.nextRoute?.first,
				onNextRouteClick = scaffold.topAppBar.nextRoute?.second ?: {},
				nextRouteEnabled = scaffold.topAppBar.nextRoute != null
			)
		},
		content = content
	)

	LaunchedEffect(key1 = scaffold.topAppBar.prevRoute) {
		scaffold.onPrevRouteChange()
	}
	LaunchedEffect(key1 = scaffold.topAppBar.nextRoute) {
		scaffold.onNextRouteChange()
	}
}