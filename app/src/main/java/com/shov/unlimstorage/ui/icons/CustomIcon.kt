package com.shov.unlimstorage.ui.icons

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun CustomIcon(
	modifier: Modifier = Modifier,
	imageVector: ImageVector?,
	contentDescription: String? = imageVector?.name,
	tint: Color = LocalContentColor.current
) {
	imageVector?.let {
		Icon(
			modifier = modifier,
			contentDescription = contentDescription,
			imageVector = imageVector,
			tint = tint
		)
	}
}
