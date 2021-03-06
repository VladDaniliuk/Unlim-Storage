package com.shov.filesfeature.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Typography
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
				textStyle = Typography().titleSmall
			)

			CustomText(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = value,
				textStyle = Typography().titleMedium
			)
		}
	}
}

@Preview
@Composable
private fun TextInfoPreview() {
	TextInfo(name = "name", value = "value")
}
