package com.shov.settingsfeature.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun PasswordField(
	modifier: Modifier = Modifier,
	passIsVisible: Boolean = false,
	password: String,
	iconAlignment: Alignment = Alignment.CenterEnd,
	iconPaddingValues: PaddingValues = PaddingValues(end = 16.dp),
	onIconClick: () -> Unit
) {
	Box(modifier = modifier) {
		Row(modifier = Modifier.align(Alignment.Center)) {
			password.forEach { symbol ->
				if (passIsVisible) {
					CustomText(
						modifier = Modifier
							.align(Alignment.CenterVertically)
							.padding(horizontal = 4.dp),
						color = MaterialTheme.colorScheme.primary,
						text = symbol.toString(),
						textStyle = Typography().headlineLarge,
						maxLines = 1
					)
				} else {
					Icon(
						modifier = Modifier
							.align(Alignment.CenterVertically)
							.padding(horizontal = 4.dp),
						imageVector = Icons.Rounded.FiberManualRecord,
						contentDescription = Icons.Rounded.FiberManualRecord.name,
						tint = MaterialTheme.colorScheme.primary
					)
				}
			}
		}

		IconButton(
			modifier = Modifier
				.align(iconAlignment)
				.padding(iconPaddingValues),
			onClick = onIconClick
		) {
			Icon(
				contentDescription = when (passIsVisible) {
					true -> Icons.Rounded.Visibility.name
					false -> Icons.Rounded.VisibilityOff.name
				},
				imageVector = when (passIsVisible) {
					true -> Icons.Rounded.Visibility
					else -> Icons.Rounded.VisibilityOff
				}
			)
		}
	}
}

@Preview
@Composable
fun PasswordFieldVisiblePreview() {
	PasswordField(
		modifier = Modifier.fillMaxWidth(),
		passIsVisible = true,
		password = "1234"
	) {}
}

@Preview
@Composable
fun PasswordFieldInvisiblePreview() {
	PasswordField(
		modifier = Modifier.fillMaxWidth(),
		password = "1234"
	) {}
}
