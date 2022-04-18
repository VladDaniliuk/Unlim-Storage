package com.shov.unlimstorage.views

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

	CustomScaffold {
		MainNavigation()
	}

	LaunchedEffect(key1 = bottomSheetViewModel.sheetState.isVisible) {
		if (bottomSheetViewModel.sheetState.isVisible.not()) {
			bottomSheetViewModel.setContent()
		}
	}
}
