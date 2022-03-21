package com.shov.settingsfeature.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.KeyboardActionButton(
	modifier: Modifier = Modifier,
	contentColor: Color = LocalContentColor.current,
	haptic: HapticFeedback = LocalHapticFeedback.current,
	onClick: (() -> Unit)?,
	onLongClick: (() -> Unit)? = null,
	enabled: Boolean = (onClick != null) or (onLongClick != null),
	content: @Composable (BoxScope.() -> Unit)?
) {
	Surface(
		modifier = modifier
			.weight(1f),
		contentColor = contentColor
	) {
		Box(
			modifier = Modifier
				.padding(all = 2.dp)
				.clip(CircleShape)
				.combinedClickable(
					enabled = enabled,
					onClick = {
						onClick?.let { onClick ->
							haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
							onClick()
						}
					},
					onLongClick = {
						onLongClick?.let { onLongClick ->
							haptic.performHapticFeedback(HapticFeedbackType.LongPress)
							onLongClick()
						}
					}
				),
			contentAlignment = Alignment.Center,
			content = content ?: {}
		)
	}
}

@Preview
@Composable
fun KeyboardActionButtonPreview() {
	Row {
		KeyboardActionButton(onClick = {}) {
			Icon(
				imageVector = Icons.Rounded.Download,
				contentDescription = ""
			)
		}
	}
}
