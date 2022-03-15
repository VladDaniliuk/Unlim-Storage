package com.shov.unlimstorage.views.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Checkbox
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.dialogs.CustomDialogContent
import com.shov.coreui.ui.dialogs.CustomHeaderText
import com.shov.coreui.ui.texts.CustomText
import com.shov.unlimstorage.R

@Composable
fun NewVersionView(
	formatArgs: Array<String>,
	isChecked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	onCancelRequest: () -> Unit,
	onCompleteRequest: () -> Unit
) {
	CustomDialogContent(
		content = {
			CustomText(
				text = stringResource(
					id = R.string.update_text,
					formatArgs = formatArgs
				),
				textStyle = Typography().body1
			)

			Row(modifier = Modifier.paddingFromBaseline(36.dp)) {
				Checkbox(
					modifier = Modifier.padding(end = 8.dp),
					checked = isChecked,
					onCheckedChange = onCheckedChange
				)

				CustomText(
					modifier = Modifier.align(Alignment.CenterVertically),
					text = stringResource(R.string.dont_show_again),
					textStyle = Typography().body1
				)
			}
		},
		header = {
			CustomHeaderText(
				stringResource(
					R.string.new_,
					stringResource(R.string.update_available)
				)
			)
		},
		onCompleteText = stringResource(R.string.download).uppercase(),
		onCancelText = stringResource(android.R.string.cancel).uppercase(),
		onCancelRequest = onCancelRequest,
		onCompleteRequest = onCompleteRequest
	)
}

@Preview
@Composable
private fun NewVersionPreview() {
	NewVersionView(
		formatArgs = arrayOf("", "", "", "", ""),
		isChecked = false,
		onCheckedChange = {},
		onCancelRequest = {}
	) {}
}
