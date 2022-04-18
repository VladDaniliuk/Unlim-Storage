package com.shov.coreui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.CustomTopAppBar
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun CustomScaffold(
	scaffold: ScaffoldViewModel = singletonViewModel(),
	content: @Composable (PaddingValues) -> Unit
) {
	@OptIn(ExperimentalMaterialApi::class)
	ModalBottomSheetLayout(
		modifier = Modifier
			.fillMaxSize()
			.windowInsetsPadding(
				WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
			),
		sheetBackgroundColor = MaterialTheme.colorScheme.surface,
		sheetContent = {
			Surface(
				modifier = Modifier
					.padding(bottom = 4.dp)
					.navigationBarsPadding()
					.imePadding()
			) {
				scaffold.sheetContent(this)
			}
		},
		sheetContentColor = MaterialTheme.colorScheme.onSurface,
		sheetShape = MaterialTheme.shapes.medium.copy(
			bottomEnd = CornerSize(0),
			bottomStart = CornerSize(0)
		),
		sheetState = scaffold.sheetState,
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
	}

	LaunchedEffect(key1 = scaffold.topAppBar.prevRoute) {
		scaffold.onPrevRouteChange()
	}
	LaunchedEffect(key1 = scaffold.topAppBar.nextRoute) {
		scaffold.onNextRouteChange()
	}
}