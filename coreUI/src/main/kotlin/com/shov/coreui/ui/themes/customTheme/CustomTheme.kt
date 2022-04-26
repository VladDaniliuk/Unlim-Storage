package com.shov.coreui.ui.themes.customTheme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shov.coreui.models.Theme
import com.shov.coreui.viewModels.CustomThemeViewModel
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun CustomTheme(
	customThemeViewModel: CustomThemeViewModel = singletonViewModel(),
	darkTheme: Boolean = isSystemInDarkTheme(),
	systemUiController: SystemUiController = rememberSystemUiController(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colorScheme = run {
			@SuppressLint("NewApi")
			when (customThemeViewModel.theme) {
				Theme.Dark -> if (customThemeViewModel.isDynamicTheme) {
					dynamicDarkColorScheme(LocalContext.current)
				} else DarkThemeColors
				Theme.Light -> if (customThemeViewModel.isDynamicTheme) {
					dynamicLightColorScheme(LocalContext.current)
				} else LightThemeColors
				Theme.System -> if (darkTheme) {
					if (customThemeViewModel.isDynamicTheme) {
						dynamicDarkColorScheme(LocalContext.current)
					} else DarkThemeColors
				} else {
					if (customThemeViewModel.isDynamicTheme) {
						dynamicLightColorScheme(LocalContext.current)
					} else LightThemeColors
				}
			}
		},
		typography = CustomTypography,
		content = content
	)

	LaunchedEffect(key1 = customThemeViewModel.theme) {
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = if (customThemeViewModel.theme == Theme.System)
				darkTheme.not()
			else customThemeViewModel.theme != Theme.Dark
		)
	}
}
