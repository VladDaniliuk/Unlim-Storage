package com.shov.unlimstorage.ui.utils

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.buttons.animation.ButtonState

@Composable
fun Transition<ButtonState>.animateComposeSize(idle: Dp = 0.dp, pressed: Dp = 0.dp) =
	animateDp(label = "compose size") { state ->
		when (state) {
			ButtonState.IDLE -> idle
			ButtonState.PRESSED -> pressed
		}
	}

@Composable
fun Transition<ButtonState>.animateComposeShapes() = animateDp(label = "Button shapes") { state ->
	when (state) {
		ButtonState.IDLE -> 4.dp
		ButtonState.PRESSED -> 100.dp
	}
}
