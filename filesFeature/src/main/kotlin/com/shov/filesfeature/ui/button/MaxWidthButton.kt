package com.shov.filesfeature.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun MaxWidthButton(text: String, onClick: () -> Unit) = Button(
	modifier = Modifier
		.fillMaxWidth()
		.padding(horizontal = 8.dp),
	onClick = onClick
) {
	CustomText(
		text = text,
		textStyle = Typography().labelLarge
	)
}

@Preview
@Composable
fun MaxWidthButtonPreview() {
	MaxWidthButton("Max width button") {}
}
