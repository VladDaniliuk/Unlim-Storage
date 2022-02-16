package com.shov.unlimstorage.ui.keyboard.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.texts.CustomText

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
			textStyle = Typography().h4
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
