package com.shov.filesfeature.ui.button

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Transition<ButtonState>.animateComposeSize(idle: Dp = 0.dp, pressed: Dp = 0.dp) =
	animateDp(label = "compose size") { state ->
		when (state) {
			ButtonState.IDLE -> idle
			ButtonState.PRESSED -> pressed
		}
	}
