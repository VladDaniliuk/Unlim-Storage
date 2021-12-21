package com.shov.unlimstorage.ui.circularProgressIndicator

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.buttons.animation.ButtonState

@Composable
fun Transition<ButtonState>.animateButtonProgressSize() =
	animateDp(label = "circular progress size") { state ->
		when (state) {
			ButtonState.IDLE -> 0.dp
			ButtonState.PRESSED -> 40.dp
		}
	}