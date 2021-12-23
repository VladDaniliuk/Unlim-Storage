package com.shov.unlimstorage.ui.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.buttons.animation.ButtonState
import com.shov.unlimstorage.ui.buttons.animation.animateButtonHorizontalPadding
import com.shov.unlimstorage.ui.buttons.animation.updateButtonTransition
import com.shov.unlimstorage.ui.utils.animateComposeShapes
import com.shov.unlimstorage.ui.utils.animateComposeSize

@Composable
fun ProgressButton(
	modifier: Modifier = Modifier,
	onClick: (onError: () -> Unit) -> Unit,
) {
	var state by remember { mutableStateOf(ButtonState.IDLE) }
	val transition = updateButtonTransition(state)
	val shapes by transition.animateComposeShapes()
	val progressSize by transition.animateComposeSize(pressed = 40.dp)
	val horizontalPadding by transition.animateButtonHorizontalPadding()

	Box(
		modifier = modifier
			.background(
				color = MaterialTheme.colors.primary,
				shape = RoundedCornerShape(shapes),
			)
			.clip(RoundedCornerShape(shapes))
			.clickable(enabled = state == ButtonState.IDLE) {
				state = ButtonState.PRESSED

				onClick {
					state = ButtonState.IDLE
				}
			}
			.padding(
				horizontal = horizontalPadding,
				vertical = 8.dp
			)
	) {
		AnimatedVisibility(
			modifier = Modifier.align(Alignment.Center),
			visible = state == ButtonState.IDLE
		) {
			Text(
				text = stringResource(R.string.create),
				color = MaterialTheme.colors.onPrimary,
			)
		}

		CircularProgressIndicator(
			color = MaterialTheme.colors.onPrimary,
			modifier = Modifier
				.align(Alignment.Center)
				.size(progressSize)
		)
	}
}

@Preview
@Composable
fun ProgressButtonPreview() {
	ProgressButton {}
}