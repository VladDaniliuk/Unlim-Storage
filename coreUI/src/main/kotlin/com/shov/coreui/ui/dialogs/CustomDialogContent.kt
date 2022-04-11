package com.shov.coreui.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText

@Composable
fun CustomDialogContent(
	header: @Composable ColumnScope.() -> Unit = {},
	onCancelRequest: (() -> Unit) = {},
	onCancelText: String? = null,
	onCompleteRequest: (() -> Unit) = {},
	onCompleteText: String? = null,
	content: @Composable ColumnScope.() -> Unit = {},
) {
	Column(
		modifier = Modifier
			.background(
				color = MaterialTheme.colorScheme.surface,
				shape = MaterialTheme.shapes.medium
			)
			.fillMaxWidth()
			.padding(
				start = 24.dp,
				end = 8.dp,
				bottom = 8.dp
			)
	) {
		this.header()

		Column(
			modifier = Modifier
				.paddingFromBaseline(top = 36.dp)
				.padding(end = 16.dp),
			content = content
		)

		Row(
			modifier = Modifier
				.align(Alignment.End)
				.padding(top = 36.dp)
		) {
			onCancelText?.let { onCancelText ->
				TextButton(onClick = onCancelRequest) {
					CustomText(
						text = onCancelText,
						textStyle = Typography().labelLarge
					)
				}
			}

			onCompleteText?.let { onCompleteText ->
				TextButton(
					modifier = Modifier.padding(start = 8.dp),
					onClick = onCompleteRequest
				) {
					CustomText(
						text = onCompleteText,
						textStyle = Typography().labelLarge
					)
				}

			}
		}
	}
}

@Preview
@Composable
private fun CustomDialogTextPreview() {
	CustomDialogContent(
		content = { Text("Text") },
		header = { CustomHeaderText("Text") },
		onCancelText = "Text",
		onCompleteText = "Text",
	)
}
