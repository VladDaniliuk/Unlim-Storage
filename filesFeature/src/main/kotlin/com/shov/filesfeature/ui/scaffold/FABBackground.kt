package com.shov.filesfeature.ui.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FABBackground(
	visible: Boolean,
	onBackgroundClick: () -> Unit,
	content: @Composable () -> Unit
) {
	content()

	AnimatedVisibility(
		visible = visible,
		enter = fadeIn(),
		exit = fadeOut()
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.Black.copy(alpha = 0.4f))
				.clickable(
					onClick = onBackgroundClick,
					indication = null,
					interactionSource = remember { MutableInteractionSource() }
				)
		)
	}
}