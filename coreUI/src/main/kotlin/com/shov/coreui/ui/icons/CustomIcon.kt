package com.shov.coreui.ui.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomIcon(
	modifier: Modifier = Modifier,
	imageVector: ImageVector?,
	contentDescription: String? = imageVector?.name,
	tint: Color = LocalContentColor.current
) {
	imageVector?.let {
		CustomIcon(
			modifier = modifier,
			painter = rememberVectorPainter(imageVector),
			contentDescription = contentDescription,
			tint = tint
		)
	}
}

@Composable
fun CustomIcon(
	modifier: Modifier = Modifier,
	painter: Painter,
	contentDescription: String? = null,
	tint: Color = LocalContentColor.current
) {
	Icon(
		modifier = modifier,
		painter = painter,
		contentDescription = contentDescription,
		tint = tint
	)
}

@Preview
@Composable
fun CustomIconPreview() {
	CustomIcon(imageVector = Icons.Default.Search)
}
