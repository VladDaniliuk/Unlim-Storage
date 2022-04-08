package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.autoupdatefeature.views.NewVersionDialog
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.viewModels.DownloadViewModel
import com.shov.coreui.viewModels.BottomSheetViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	downloadViewModel: DownloadViewModel = singletonViewModel()
) {
	NewVersionDialog(setProgress = downloadViewModel::setProgress)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.windowInsetsPadding(
				WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
			)
	) {
		ModalBottomSheetLayout(
			modifier = Modifier.weight(1f),
			sheetState = bottomSheetViewModel.sheetState,
			sheetContent = {
				bottomSheetViewModel.sheetContent(this)

				Spacer(
					modifier = Modifier
						.padding(bottom = 4.dp)
						.navigationBarsPadding()
						.imePadding()
				)
			},
			sheetShape = MaterialTheme.shapes.large.copy(
				bottomEnd = CornerSize(0),
				bottomStart = CornerSize(0)
			),
			content = {
				CustomScaffold {
					MainNavigation()
				}
			}
		)
	}

	LaunchedEffect(key1 = bottomSheetViewModel.sheetState.isVisible) {
		if (bottomSheetViewModel.sheetState.isVisible.not()) {
			bottomSheetViewModel.setContent()
		}
	}
}
