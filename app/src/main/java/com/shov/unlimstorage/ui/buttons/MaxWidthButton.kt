package com.shov.unlimstorage.ui.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.texts.CustomText

@Composable
fun MaxWidthButton(text: String, onClick: () -> Unit) = Button(
	modifier = Modifier
		.fillMaxWidth()
		.padding(horizontal = 8.dp),
	shape = CircleShape,
	onClick = onClick
) {
	CustomText(
		text = text,
		textStyle = Typography().button
	)
}

@Preview
@Composable
fun MaxWidthButtonPreview() {
	MaxWidthButton("Max width button") {}
}
