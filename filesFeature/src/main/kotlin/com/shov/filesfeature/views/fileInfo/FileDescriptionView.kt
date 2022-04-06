package com.shov.filesfeature.views.fileInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R

@Composable
fun FileDescriptionView(description: String?, onIconClick: () -> Unit) {
	Column {
		Row(modifier = Modifier.fillMaxWidth()) {
			CustomText(
				modifier = Modifier
					.padding(start = 16.dp)
					.weight(1f)
					.align(Alignment.CenterVertically),
				text = stringResource(R.string.description),
				textStyle = Typography().subtitle2
			)

			IconButton(
				modifier = Modifier.padding(
					top = 8.dp,
					end = 8.dp
				),
				onClick = onIconClick
			) {
				Icon(
					imageVector = Icons.Rounded.Edit,
					contentDescription = Icons.Rounded.Edit.name
				)
			}
		}

		CustomText(
			modifier = Modifier.padding(horizontal = 16.dp),
			text = description,
			textStyle = Typography().subtitle1
		)

		Divider()
	}
}

@Preview
@Composable
fun FileDescriptionPreview() {
	FileDescriptionView(description = "Description of file") {}
}
