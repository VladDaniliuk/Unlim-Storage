package com.shov.settingsfeature.views.theme

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.CustomThemeViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.settingsfeature.R
import com.shov.settingsfeature.viewModels.ThemeViewModel

@Composable
fun ThemeScreen(
	customThemeViewModel: CustomThemeViewModel = singletonViewModel(),
	themeViewModel: ThemeViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	CustomScaffold(
		title = {
			Text(context.getString(R.string.theme_settings))
		},
		navigationIcon = {
			IconButton(onClick = navigationViewModel::popBack) {
				Icon(
					imageVector = Icons.Rounded.ArrowBack,
					contentDescription = null
				)
			}
		}
	) { paddingValues ->
		ThemeView(
			changeDynamicTheme = customThemeViewModel::changeDynamicTheme,
			changeTheme = customThemeViewModel::changeTheme,
			expandMenu = themeViewModel::expand,
			isDynamicTheme = customThemeViewModel.isDynamicTheme,
			isExpanded = themeViewModel.isExpanded,
			paddingValues=paddingValues,
			theme = customThemeViewModel.theme,
		)
	}
}
