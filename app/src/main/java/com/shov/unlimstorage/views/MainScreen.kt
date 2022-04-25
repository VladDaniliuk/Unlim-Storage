package com.shov.unlimstorage.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.shov.autoupdatefeature.views.NewVersionDialog
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.viewModels.DownloadViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation

@Composable
fun MainScreen(
	downloadViewModel: DownloadViewModel = singletonViewModel()
) {
	NewVersionDialog(setProgress = downloadViewModel::onDownload)

	@OptIn(ExperimentalMaterialNavigationApi::class)
	CustomScaffold { bottomSheetNavigator ->
		MainNavigation(rememberNavController(bottomSheetNavigator))
	}
}
