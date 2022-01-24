package com.shov.unlimstorage.values

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CustomTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	systemUiController: SystemUiController = rememberSystemUiController(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (darkTheme) DARK_THEME else LIGHT_THEME,
		content = content,
		shapes = Shapes(
			small = RoundedCornerShape(size = 4.dp),
			medium = RoundedCornerShape(size = 8.dp),
			large = RoundedCornerShape(size = 12.dp)
		)
	)

	SideEffect {
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = false
		)
	}
}

val DARK_THEME = Colors(
	primary = DEEP_PURPLE_900_LIGHT,
	primaryVariant = DEEP_PURPLE_900_DARK,
	onPrimary = WHITE,
	secondary = LIGHT_BLUE_900,
	secondaryVariant = LIGHT_BLUE_900_DARK,
	onSecondary = WHITE,
	background = BACKGROUND,
	error = ERROR_DARK,
	surface = BACKGROUND,
	onBackground = WHITE,
	onSurface = WHITE,
	onError = BLACK,
	isLight = false
)

val LIGHT_THEME = Colors(
	primary = DEEP_PURPLE_900,
	primaryVariant = DEEP_PURPLE_900_DARK,
	onPrimary = WHITE,
	secondary = LIGHT_BLUE_900,
	secondaryVariant = LIGHT_BLUE_900_DARK,
	onSecondary = BLACK,
	background = WHITE,
	error = ERROR_LIGHT,
	surface = WHITE,
	onBackground = BLACK,
	onSurface = BLACK,
	onError = WHITE,
	isLight = true
)
