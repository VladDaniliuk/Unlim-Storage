package com.shov.coreui.ui.themes.customTheme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CustomTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	systemUiController: SystemUiController = rememberSystemUiController(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			if (darkTheme) {
				dynamicDarkColorScheme(LocalContext.current)
			} else {
				dynamicLightColorScheme(LocalContext.current)
			}
		} else if (darkTheme) DarkThemeColors else LightThemeColors,
		typography = CustomTypography
	) {
		content()
	}

	SideEffect {
		systemUiController.setStatusBarColor(
			color = Color.Transparent,
			darkIcons = darkTheme.not()
		)

		systemUiController.setNavigationBarColor(
			color = Color.Transparent,
			darkIcons = darkTheme.not()
		)
	}
}
