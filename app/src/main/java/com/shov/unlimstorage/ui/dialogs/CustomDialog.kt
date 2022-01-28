package com.shov.unlimstorage.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.CustomText

@Composable
fun CustomDialogContent(
	header: @Composable ColumnScope.() -> Unit = {},
	onCancelRequest: (() -> Unit) = {},
	onCancelText: String? = null,
	onCompleteRequest: (() -> Unit) = {},
	onCompleteText: String? = null,
	content: @Composable ColumnScope.() -> Unit = {},
) = Column(
	modifier = Modifier
		.background(
			color = MaterialTheme.colors.surface,
			shape = MaterialTheme.shapes.medium
		)
		.fillMaxWidth()
		.padding(
			start = 24.dp,
			end = 8.dp,
			bottom = 8.dp
		)
) {
	header.invoke(this)

	Column(
		modifier = Modifier
			.paddingFromBaseline(top = 36.dp)
			.padding(end = 16.dp),
		content = content
	)

	if ((onCancelText != null) or (onCompleteText != null)) {
		Row(
			modifier = Modifier
				.align(Alignment.End)
				.padding(top = 36.dp)
		) {
			onCancelText?.let { onCancelText ->
				TextButton(onClick = onCancelRequest) {
					CustomText(
						text = onCancelText,
						textStyle = Typography().button,
						color = MaterialTheme.colors.primary
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
						textStyle = Typography().button,
						color = MaterialTheme.colors.primary
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun CustomDialogTextPreview() {
	CustomDialogContent(
		header = { CustomHeaderText("Text") },
		onCancelText = "Text",
		onCompleteText = "Text"
	) { Text("Text") }
}

@Preview
@Composable
fun CustomDialogIconPreview() {
	CustomDialogContent(
		header = { CustomHeaderIcon(Icons.Default.Sailing) },
		onCancelText = "Text",
		onCompleteText = "Text",
	) { Text("Text") }
}
