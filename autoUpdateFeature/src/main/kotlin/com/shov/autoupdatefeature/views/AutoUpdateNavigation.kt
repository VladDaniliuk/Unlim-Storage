package com.shov.autoupdatefeature.views

import androidx.navigation.NavGraphBuilder
import com.shov.coreui.ui.horizontalComposable
import com.shov.coreutils.values.Screen


fun NavGraphBuilder.autoUpdateComposable() {
	horizontalComposable(Screen.Updates.route) {
		UpdateScreen()
	}
}
