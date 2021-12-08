package com.shov.unlimstorage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dangerous
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconButton(
	contentDescription: String = "",
	image: ImageVector,
	text: String = "",
	onClick: () -> Unit = {}
) {
	Column(
		modifier = Modifier
			.padding(start = 8.dp)
			.clip(CircleShape)
			.clickable(onClick = onClick)
			.padding(all = 4.dp)
	) {
		Icon(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			imageVector = image,
			contentDescription = contentDescription
		)

		Text(
			text = text,
			fontSize = Typography().subtitle1.fontSize,
			fontStyle = Typography().subtitle1.fontStyle,
			fontWeight = Typography().subtitle1.fontWeight,
		)
	}
}

@Preview
@Composable
fun CustomIconButtonPreview() {
	CustomIconButton(
		image = Icons.Rounded.Dangerous,
		text = "Text"
	)
}