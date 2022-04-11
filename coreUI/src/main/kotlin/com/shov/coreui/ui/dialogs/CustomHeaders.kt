package com.shov.coreui.ui.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun CustomHeaderText(text: String?) {
	text?.let {
		CustomText(
			modifier = Modifier
				.paddingFromBaseline(top = 40.dp)
				.padding(end = 16.dp),
			text = text,
			textStyle = Typography().titleLarge
		)
	}
}