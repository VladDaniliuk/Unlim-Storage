package com.shov.coreui.ui.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun TextButton(
	modifier: Modifier = Modifier,
	text: String,
	enabled: Boolean = true,
	maxLines: Int = 1,
	indicationVerticalPadding: Dp = 0.dp,
	indicationHorizontalPadding: Dp = 0.dp,
	onClick: () -> Unit
) = CustomText(
	modifier = modifier
		.clip(CircleShape)
		.clickable(
			enabled = enabled,
			interactionSource = remember { MutableInteractionSource() },
			indication = rememberRipple(bounded = false),
			onClick = onClick
		)
		.padding(
			horizontal = indicationHorizontalPadding,
			vertical = indicationVerticalPadding
		),
	text = text,
	maxLines = maxLines,
	textAlign = TextAlign.Center
)

@Composable
fun TextButton(
	modifier: Modifier = Modifier,
	text: String,
	enabled: Boolean = true,
	maxLines: Int = 1,
	indicationPadding: Dp = 0.dp,
	onClick: () -> Unit
) = TextButton(
	modifier = modifier,
	text = text,
	enabled = enabled,
	maxLines = maxLines,
	indicationVerticalPadding = indicationPadding,
	indicationHorizontalPadding = indicationPadding,
	onClick = onClick
)

@Preview
@Composable
private fun TextButtonPreview() {
	TextButton(text = "Preview", indicationPadding = 8.dp) {}
}
