package com.shov.unlimstorage.ui.buttons.animation

import androidx.compose.animation.*
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.shov.unlimstorage.ui.icons.CustomIcon

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconButton(
	modifier: Modifier = Modifier,
	imageVector: ImageVector?,
	tint: Color = LocalContentColor.current,
	visible: Boolean,
	onClick: () -> Unit
) {
	AnimatedContent(
		modifier = modifier,
		targetState = imageVector,
		transitionSpec = {
			fadeIn() + scaleIn() with fadeOut() + scaleOut()
		}
	) { icon ->
		IconButton(
			onClick = onClick,
			enabled = visible
		) {
			CustomIcon(
				imageVector = icon,
				tint = tint
			)
		}
	}
}
