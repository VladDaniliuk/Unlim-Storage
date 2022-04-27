package com.shov.settingsfeature.views.theme

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.CustomThemeViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R
import com.shov.settingsfeature.viewModels.ThemeViewModel

@Composable
fun ThemeScreen(
	customThemeViewModel: CustomThemeViewModel = singletonViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	themeViewModel: ThemeViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	ThemeView(
		theme = customThemeViewModel.theme,
		expandMenu = themeViewModel::expand,
		isExpanded = themeViewModel.isExpanded,
		changeTheme = customThemeViewModel::changeTheme,
		isDynamicTheme = customThemeViewModel.isDynamicTheme,
		changeDynamicTheme = customThemeViewModel::changeDynamicTheme,
	)

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			prevRoute = Icons.Rounded.ArrowBack to navigationViewModel::popBack,
			title = context.getString(R.string.theme_settings)
		)
	}
}
