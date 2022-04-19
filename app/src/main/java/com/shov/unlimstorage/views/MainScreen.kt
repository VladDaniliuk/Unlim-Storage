package com.shov.unlimstorage.views

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.shov.autoupdatefeature.views.NewVersionDialog
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.viewModels.DownloadViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation

@Composable
fun MainScreen(
	scaffold: ScaffoldViewModel = singletonViewModel(),
	downloadViewModel: DownloadViewModel = singletonViewModel()
) {
	NewVersionDialog(setProgress = downloadViewModel::onDownload)

	@OptIn(ExperimentalMaterialNavigationApi::class)
	CustomScaffold { bottomSheetNavigator ->
		MainNavigation(rememberNavController(bottomSheetNavigator))
	}

	@OptIn(ExperimentalMaterialApi::class)
	LaunchedEffect(key1 = scaffold.sheetState.isVisible) {
		if (scaffold.sheetState.isVisible.not()) {
			scaffold.setContent()
		}
	}
}
