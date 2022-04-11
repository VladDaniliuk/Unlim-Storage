package com.shov.coreui.ui.themes.customTheme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shov.coreui.ui.themes.CustomRippleTheme
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@Composable
fun CustomTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	systemUiController: SystemUiController = rememberSystemUiController(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (darkTheme) DarkTheme else LightTheme,
		shapes = Shapes(
			small = RoundedCornerShape(size = 8.dp),
			medium = RoundedCornerShape(size = 12.dp),
			large = RoundedCornerShape(size = 16.dp),
		)
	) {
		MaterialTheme3(
			colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
				if (darkTheme) {
					dynamicDarkColorScheme(LocalContext.current)
				} else {
					dynamicLightColorScheme(LocalContext.current)
				}
			} else if (darkTheme) DarkThemeColors else LightThemeColors,
			typography = CustomTypography
		) {
			CompositionLocalProvider(
				LocalRippleTheme provides CustomRippleTheme(),
				content = content
			)
		}
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
