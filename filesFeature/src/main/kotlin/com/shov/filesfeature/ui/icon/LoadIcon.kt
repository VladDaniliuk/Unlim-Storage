package com.shov.filesfeature.ui.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadIcon(
	modifier: Modifier = Modifier,
	contentDescription: String = "",
	defaultImageVector: ImageVector = Icons.Rounded.AccountCircle,
	defaultTint: Color = Color.Unspecified,
	iconLink: String? = null,
	iconTint: Color = Color.Unspecified
) {
	Box(modifier = modifier) {
		iconLink?.let {
			Icon(
				modifier = Modifier.fillMaxSize(),
				painter = rememberAsyncImagePainter(iconLink),
				contentDescription = contentDescription,
				tint = iconTint
			)
		} ?: Icon(
			modifier = Modifier.fillMaxSize(),
			imageVector = defaultImageVector,
			contentDescription = contentDescription,
			tint = defaultTint
		)
	}
}
