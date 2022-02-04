package com.shov.unlimstorage.ui.buttons.animation

import androidx.compose.animation.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconButton(
	modifier: Modifier = Modifier,
	imageVector: ImageVector?,
	tint: Color = LocalContentColor.current,
	visible: Boolean,
	onClick: () -> Unit
) {
	AnimatedVisibility(
		modifier = modifier,
		enter = fadeIn() + scaleIn(),
		exit = fadeOut() + scaleOut(),
		visible = visible
	) {
		imageVector?.let { imageVector ->
			AnimatedContent(
				targetState = imageVector,
				transitionSpec = {
					fadeIn() + scaleIn() with fadeOut() + scaleOut()
				}
			) { image ->
				IconButton(onClick = onClick) {
					Icon(
						contentDescription = image.name,
						imageVector = image,
						tint = tint
					)
				}
			}
		}
	}
}
