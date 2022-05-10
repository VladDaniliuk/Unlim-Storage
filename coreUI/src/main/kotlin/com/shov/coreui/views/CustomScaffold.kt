package com.shov.coreui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.shov.coreui.ui.CustomTopAppBar
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun CustomScaffold(
	bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	content: @Composable (BottomSheetNavigator) -> Unit
) {
	ModalBottomSheetLayout(
		bottomSheetNavigator = bottomSheetNavigator,
		modifier = Modifier
			.fillMaxSize()
			.windowInsetsPadding(
				WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
			),
		sheetShape = MaterialTheme.shapes.medium.copy(
			bottomEnd = CornerSize(0.dp),
			bottomStart = CornerSize(0.dp)
		),
		sheetBackgroundColor = MaterialTheme.colorScheme.surface,
		sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface)
	) {
		@OptIn(ExperimentalMaterial3Api::class)
		Scaffold(
			topBar = { CustomTopAppBar() },
			snackbarHost = {
				SnackbarHost(
					modifier = Modifier.navigationBarsPadding(),
					hostState = scaffold.snackbarHostState
				)
			}
		) {
			Box(modifier = Modifier.padding(paddingValues = it)) {
				content(bottomSheetNavigator)
			}
		}
	}
}
