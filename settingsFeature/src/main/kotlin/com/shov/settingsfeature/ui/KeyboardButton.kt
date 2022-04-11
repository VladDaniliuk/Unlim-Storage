package com.shov.settingsfeature.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun RowScope.KeyboardButton(
	modifier: Modifier = Modifier,
	haptic: HapticFeedback = LocalHapticFeedback.current,
	int: Int, onClick: () -> Unit
) {
	TextButton(
		modifier = modifier
			.weight(1f)
			.padding(all = 2.dp)
			.clip(CircleShape),
		onClick = {
			haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
			onClick()
		}
	) {
		CustomText(
			text = int.toString(),
			textStyle = Typography().headlineLarge
		)
	}
}

@Preview
@Composable
fun KeyboardButtonPreview() {
	Row {
		KeyboardButton(int = 0) {}
	}
}
