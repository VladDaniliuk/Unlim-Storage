package com.shov.filesfeature.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun TextInfo(name: String, value: String?) {
	value?.let {
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
}

@Preview
@Composable
private fun TextInfoPreview() {
	TextInfo(name = "name", value = "value")
}
