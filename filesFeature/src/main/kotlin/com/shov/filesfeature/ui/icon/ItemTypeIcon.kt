package com.shov.filesfeature.ui.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentColor
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
import com.shov.coreui.ui.icons.CustomIcon

@Composable
fun ItemTypeIcon(
	modifier: Modifier = Modifier,
	iconSize: Dp = 64.dp,
	mainIcon: ImageVector? = Icons.Rounded.Folder,
	mainTint: Color = LocalContentColor.current,
	secondaryIcon: Painter? = null,
	secondaryTint: Color = Color.Unspecified,
	secondaryAlignment: Alignment = Alignment.Center,
) {
	mainIcon?.let {
		Box(modifier = modifier) {
			CustomIcon(
				modifier = Modifier.size(iconSize),
				imageVector = mainIcon,
				tint = mainTint
			)

			secondaryIcon?.let { icon ->
				CustomIcon(
					modifier = Modifier.align(secondaryAlignment),
					painter = icon,
					tint = secondaryTint
				)
			}
		}
	}
}

@Preview
@Composable
private fun ItemTypeIconPreview() {
	ItemTypeIcon()
}
