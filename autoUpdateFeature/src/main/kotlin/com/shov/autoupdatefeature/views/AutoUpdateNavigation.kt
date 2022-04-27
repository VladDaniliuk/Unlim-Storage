package com.shov.autoupdatefeature.views

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shov.coreutils.values.Screen


fun NavGraphBuilder.autoUpdateComposable() {
	composable(Screen.Updates.route) {
		UpdateScreen()
	}
}