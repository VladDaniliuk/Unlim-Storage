package com.shov.unlimstorage.ui.texts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextInfo(name: String, value: String) {
	Column {
		CustomText(
			modifier = Modifier.padding(horizontal = 16.dp),
			text = name,
			textStyle = Typography().subtitle2
		)

		CustomText(
			modifier = Modifier.padding(horizontal = 16.dp),
			text = value,
			textStyle = Typography().subtitle1
		)
	}
}
