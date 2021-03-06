package com.shov.filesfeature.ui.button

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun updateButtonTransition(targetState: ButtonState) =
	updateTransition(targetState = targetState, label = "Button click")

@Composable
fun Transition<ButtonState>.animateButtonHorizontalPadding() =
	animateDp(label = "Button horizontal padding") { state ->
		when (state) {
			ButtonState.IDLE -> 16.dp
			ButtonState.PRESSED -> 8.dp
		}
	}
