package com.shov.coreui.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class CustomRippleTheme : RippleTheme {
	@Composable
	override fun defaultColor(): Color = LocalContentColor.current

	@Composable
	override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
		LocalContentColor.current,
		lightTheme = !isSystemInDarkTheme()
	)
}
