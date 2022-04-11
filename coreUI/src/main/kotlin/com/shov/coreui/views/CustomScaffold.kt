package com.shov.coreui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
	@OptIn(ExperimentalMaterial3Api::class)
	Scaffold(
		topBar = {
			CustomTopAppBar(
				prevRouteImageVector = scaffold.topAppBar.prevRoute?.first,
				onPrevRouteClick = scaffold.topAppBar.prevRoute?.second ?: {},
				prevRouteEnabled = scaffold.topAppBar.prevRoute != null,
				title = scaffold.topAppBar.title,
				nextRouteImageVector = scaffold.topAppBar.nextRoute?.first,
				onNextRouteClick = scaffold.topAppBar.nextRoute?.second ?: {},
				nextRouteEnabled = scaffold.topAppBar.nextRoute != null,
				scrollBehavior = scaffold.scrollBehavior
			)
		},
		snackbarHost = {
			SnackbarHost(
				modifier = Modifier.navigationBarsPadding(),
				hostState = scaffold.snackbarHostState
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