package com.shov.unlimstorage.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shov.unlimstorage.ui.CustomText

@Composable
fun CustomDialog(
	content: @Composable ColumnScope.() -> Unit = {},
	header: @Composable ColumnScope.() -> Unit = {},
	onCancelRequest: (() -> Unit) = {},
	onCancelText: String? = null,
	onCompleteRequest: (() -> Unit) = {},
	onCompleteText: String? = null,
	onDismissRequest: () -> Unit,
) = Dialog(onDismissRequest = onDismissRequest) {
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
