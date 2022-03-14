package com.shov.coreui.ui.buttons

import androidx.compose.animation.*
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.ui.icons.CustomIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconButton(
	modifier: Modifier = Modifier,
	imageVector: ImageVector?,
	tint: Color = LocalContentColor.current,
	enabled: Boolean,
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
			enabled = enabled
		) {
			CustomIcon(
				imageVector = icon,
				tint = tint
			)
		}
	}
}

@Preview
@Composable
fun AnimatedIconButtonPreview() {
	AnimatedIconButton(
		imageVector = Icons.Default.Face,
		enabled = true
	) {}
}

@Preview
@Composable
fun AnimatedIconButtonAnimationPreview() {
	val coroutine: CoroutineScope = rememberCoroutineScope()

	var image by remember { mutableStateOf<ImageVector?>(null) }

	AnimatedIconButton(
		imageVector = image,
		enabled = false
	) {}

	LaunchedEffect(key1 = null) {
		coroutine.launch {
			delay(1000)
			image = Icons.Default.Face
			delay(1000)
			image = Icons.Default.Search
			delay(1000)
			image = null
		}
	}
}
