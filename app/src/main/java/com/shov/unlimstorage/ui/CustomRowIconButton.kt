package com.shov.unlimstorage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.CustomIconButton(imageVector: ImageVector, text: String, onClick: () -> Unit) {
	CustomIconButton(
		painter = rememberVectorPainter(imageVector),
		text = text,
		tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
		onClick = onClick
	)
}

@Composable
fun RowScope.CustomIconButton(
	painter: Painter,
	text: String,
	tint: Color = Color.Unspecified,
	onClick: () -> Unit
) {
	Column(
		modifier = Modifier
			.padding(horizontal = 4.dp)
			.clip(CircleShape)
			.clickable(onClick = onClick)
			.weight(1f)
	) {
		Icon(
			modifier = Modifier
				.padding(top = 8.dp)
				.align(Alignment.CenterHorizontally),
			painter = painter,
			contentDescription = text,
			tint = tint
		)

		Text(
			modifier = Modifier
				.padding(bottom = 8.dp)
				.fillMaxWidth(),
			text = text,
			textAlign = TextAlign.Center
		)
	}
}
