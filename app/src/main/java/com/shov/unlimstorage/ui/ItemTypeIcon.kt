package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ItemTypeIcon(
	modifier: Modifier = Modifier,
	iconSize: Dp = 64.dp,
	mainIcon: ImageVector = Icons.Rounded.Folder,
	contentDescription: String = "",
	mainTint: Color = Color.Unspecified,
	secondaryIcon: Painter? = null,
	secondaryTint: Color = Color.Unspecified,
	secondaryAlignment: Alignment = Alignment.Center,
) {
	Box(modifier = modifier) {
		Icon(
			modifier = Modifier.size(iconSize),
			imageVector = mainIcon,
			contentDescription = contentDescription,
			tint = mainTint
		)

		secondaryIcon?.let { icon ->
			Icon(
				modifier = Modifier.align(secondaryAlignment),
				painter = icon,
				contentDescription = contentDescription,
				tint = secondaryTint
			)
		}
	}
}

@Preview
@Composable
fun ItemTypeIconPreview() {
	ItemTypeIcon()
}
