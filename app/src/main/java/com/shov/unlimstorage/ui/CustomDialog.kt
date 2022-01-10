package com.shov.unlimstorage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.texts.CustomText

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

@Composable
fun ColumnScope.CustomHeaderIcon(icon: ImageVector) = Icon(
	contentDescription = icon.name,
	imageVector = icon,
	modifier = Modifier
		.align(Alignment.CenterHorizontally)
		.padding(top = 12.dp)
		.size(48.dp),
	tint = MaterialTheme.colors.onSurface
)

@Composable
fun customHeaderText(text: String?) = text?.let {
	CustomText(
		modifier = Modifier
			.paddingFromBaseline(top = 40.dp)
			.padding(end = 16.dp),
		text = text,
		textStyle = Typography().h6
	)
}

@Preview
@Composable
fun CustomDialogTextPreview() {
	CustomDialogContent(
		content = { Text("Text") },
		header = { customHeaderText("Text") },
		onCancelText = "Text",
		onCompleteText = "Text",
		onCancelRequest = {},
	)
}

@Preview
@Composable
fun CustomDialogIconPreview() {
	CustomDialogContent(
		content = { Text("Text") },
		header = { CustomHeaderIcon(Icons.Default.Sailing) },
		onCancelText = "Text",
		onCompleteText = "Text",
		onCancelRequest = {},
	)
}
