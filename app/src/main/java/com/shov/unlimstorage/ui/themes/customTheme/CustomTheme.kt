package com.shov.unlimstorage.ui.themes.customTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shov.unlimstorage.ui.themes.CustomRippleTheme

@Composable
fun CustomTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	systemUiController: SystemUiController = rememberSystemUiController(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (darkTheme) DarkTheme else LightTheme,
		shapes = Shapes(
			small = RoundedCornerShape(size = 4.dp),
			medium = RoundedCornerShape(size = 8.dp),
			large = RoundedCornerShape(size = 12.dp)
		)
	) {
		CompositionLocalProvider(
			LocalRippleTheme provides CustomRippleTheme(),
			content = content
		)
	}

	SideEffect {
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = darkTheme
		)
	}
}
