package com.shov.unlimstorage.ui.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.ui.CustomText

@Composable
fun CustomHeaderDialog(
	header: String? = null,
	onCancelRequest: (() -> Unit) = {},
	onCancelText: String? = null,
	onCompleteRequest: (() -> Unit) = {},
	onCompleteText: String? = null,
	onDismissRequest: () -> Unit,
	content: @Composable ColumnScope.() -> Unit = {}
) = CustomDialog(
	content = content,
	header = {
		header?.let { text ->
			CustomText(
				modifier = Modifier
					.paddingFromBaseline(top = 40.dp)
					.padding(end = 16.dp),
				text = text,
				textStyle = Typography().h6
			)
		}
	},
	onCancelRequest = onCancelRequest,
	onCancelText = onCancelText,
	onCompleteRequest = onCompleteRequest,
	onCompleteText = onCompleteText,
	onDismissRequest = onDismissRequest,
)
