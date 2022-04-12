package com.shov.filesfeature.views.fileInfo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.icons.CustomIcon
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
				textStyle = Typography().titleSmall
			)

			IconButton(
				modifier = Modifier.padding(
					top = 8.dp,
					end = 8.dp
				),
				onClick = onIconClick
			) {
				CustomIcon(imageVector = Icons.Rounded.Edit)
			}
		}

		CustomText(
			modifier = Modifier.padding(horizontal = 16.dp),
			text = description,
			textStyle = Typography().titleMedium
		)

		Divider()
	}
}

@Preview
@Composable
fun FileDescriptionPreview() {
	FileDescriptionView(description = "Description of file") {}
}
