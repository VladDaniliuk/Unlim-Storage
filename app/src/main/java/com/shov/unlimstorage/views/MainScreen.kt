package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.autoupdatefeature.views.NewVersionDialog
import com.shov.coreui.viewModels.BottomSheetViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.viewModels.DownloadViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	downloadViewModel: DownloadViewModel = singletonViewModel()
) {
	NewVersionDialog(setProgress = downloadViewModel::onDownload)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.windowInsetsPadding(
				WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
			)
	) {
		ModalBottomSheetLayout(
			modifier = Modifier.weight(1f),
			sheetBackgroundColor = MaterialTheme.colorScheme.surface,
			sheetContent = {
				Surface(
					modifier = Modifier
						.padding(bottom = 4.dp)
						.navigationBarsPadding()
						.imePadding()
				) {
					bottomSheetViewModel.sheetContent(this)
				}
			},
			sheetContentColor = MaterialTheme.colorScheme.onSurface,
			sheetShape = MaterialTheme.shapes.large.copy(
				bottomEnd = CornerSize(0),
				bottomStart = CornerSize(0)
			),
			sheetState = bottomSheetViewModel.sheetState,
		) {
			CustomScaffold {
				MainNavigation()
			}
		}
	}

	LaunchedEffect(key1 = bottomSheetViewModel.sheetState.isVisible) {
		if (bottomSheetViewModel.sheetState.isVisible.not()) {
			bottomSheetViewModel.setContent()
		}
	}
}
