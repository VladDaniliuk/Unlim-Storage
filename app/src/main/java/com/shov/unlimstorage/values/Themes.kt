package com.shov.unlimstorage.values

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CustomTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (darkTheme) DARK_THEME else LIGHT_THEME,
		content = content
	)
}

val DARK_THEME = Colors(
	primary = DEEP_PURPLE_900,
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
