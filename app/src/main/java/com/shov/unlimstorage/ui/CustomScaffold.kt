package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.coreui.ui.CustomTopAppBar
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

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
				modifier = Modifier.navigationBarsPadding(
					start = false,
					end = false
				)
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

@Composable
fun FABScaffold(onClick: () -> Unit, content: @Composable (PaddingValues) -> Unit) {
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				modifier = Modifier.navigationBarsPadding(
					start = false,
					end = false
				),
				onClick = onClick
			) {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = Icons.Rounded.Add.name,
					tint = Color.White
				)
			}
		},
		content = content
	)
}
