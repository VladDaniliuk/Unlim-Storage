package com.shov.unlimstorage.ui.icons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.themes.CustomTheme

@Composable
fun IconButton(
	modifier: Modifier = Modifier,
	imageVector: ImageVector,
	contentDescription: String = imageVector.name,
	tint: Color = LocalContentColor.current,
	enabled: Boolean = true,
	ripplePadding: Dp = 0.dp,
	onClick: () -> Unit,
) = Box(
	modifier = modifier
		.clip(CircleShape)
		.clickable(
			onClick = onClick,
			enabled = enabled,
			interactionSource = remember { MutableInteractionSource() },
			indication = rememberRipple(bounded = false)
		)
		.padding(all = ripplePadding),
	contentAlignment = Alignment.Center
) {
	Icon(
		imageVector = imageVector,
		contentDescription = contentDescription,
		tint = tint
	)
}

@Preview
@Composable
fun IconButtonPreview() {
	CustomTheme {
		IconButton(imageVector = Icons.Rounded.Download, ripplePadding = 6.dp) {}
	}
}
