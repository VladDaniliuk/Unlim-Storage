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

@Composable
fun CustomText(
	modifier: Modifier = Modifier,
	text: String? = "",
	textStyle: TextStyle = Typography().subtitle1,
	overflow: TextOverflow = TextOverflow.Clip,
	maxLines: Int = Int.MAX_VALUE,
	textAlign: TextAlign? = null,
	color: Color = LocalContentColor.current
) {
	Text(
		modifier = modifier,
		text = text ?: "",
		fontSize = textStyle.fontSize,
		fontStyle = textStyle.fontStyle,
		fontWeight = textStyle.fontWeight,
		overflow = overflow,
		maxLines = maxLines,
		textAlign = textAlign,
		color = color
	)
}
