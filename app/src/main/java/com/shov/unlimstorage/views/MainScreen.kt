package com.shov.unlimstorage.views

import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.shov.autoupdatefeature.views.NewVersionDialog
import com.shov.unlimstorage.views.navigations.MainNavigation

@Composable
fun MainScreen() {
	NewVersionDialog()

	@OptIn(ExperimentalMaterialNavigationApi::class)
	MainNavigation()
}
