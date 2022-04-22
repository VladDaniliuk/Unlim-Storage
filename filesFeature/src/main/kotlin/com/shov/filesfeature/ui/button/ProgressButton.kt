package com.shov.filesfeature.ui.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R

@Composable
fun ProgressButton(
	modifier: Modifier = Modifier,
	onClick: (onError: () -> Unit) -> Unit,
) {
	var state by remember { mutableStateOf(ButtonState.IDLE) }
	val transition = updateButtonTransition(state)
	val progressSize by transition.animateComposeSize(pressed = 40.dp)
	val horizontalPadding by transition.animateButtonHorizontalPadding()

	Box(
		modifier = modifier
			.background(
				color = MaterialTheme.colorScheme.primary,
				shape = CircleShape,
			)
			.clip(CircleShape)
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
			CustomText(
				color = MaterialTheme.colorScheme.onPrimary,
				text = stringResource(R.string.create),
				textStyle = Typography().labelLarge
			)
		}

		CircularProgressIndicator(
			color = MaterialTheme.colorScheme.onPrimary,
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
