package com.shov.unlimstorage.ui.texts

import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomText(
	modifier: Modifier = Modifier,
	color: Color = LocalContentColor.current,
	letterSpacing: TextUnit = TextUnit.Unspecified,
	maxLines: Int = Int.MAX_VALUE,
	overflow: TextOverflow = TextOverflow.Clip,
	text: String?,
	textAlign: TextAlign? = null,
	textStyle: TextStyle = Typography().subtitle1
) {
	Text(
		modifier = modifier,
		color = color,
		fontSize = textStyle.fontSize,
		fontStyle = textStyle.fontStyle,
		fontWeight = textStyle.fontWeight,
		letterSpacing = letterSpacing,
		maxLines = maxLines,
		overflow = overflow,
		text = text ?: "",
		textAlign = textAlign
	)
}
