package com.shov.unlimstorage.ui.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconDialog(
	imageVector: ImageVector = Icons.Rounded.Folder,
	onCancelRequest: (() -> Unit) = {},
	onCancelText: String? = null,
	onCompleteRequest: (() -> Unit) = {},
	onCompleteText: String? = null,
	onDismissRequest: () -> Unit,
	content: @Composable ColumnScope.() -> Unit = {},
) = CustomDialog(
	content = content,
	header = {
		Icon(
			contentDescription = imageVector.name,
			imageVector = imageVector,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(top = 12.dp)
				.size(48.dp),
			tint = MaterialTheme.colors.onSurface
		)
	},
	onCancelRequest = onCancelRequest,
	onCancelText = onCancelText,
	onCompleteRequest = onCompleteRequest,
	onCompleteText = onCompleteText,
	onDismissRequest = onDismissRequest,
)
