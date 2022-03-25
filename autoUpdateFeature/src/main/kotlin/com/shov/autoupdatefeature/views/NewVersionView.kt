package com.shov.autoupdatefeature.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shov.autoupdatefeature.R

@Composable
fun NewVersionView(
	checkBoxChecked: Boolean,
	lastReleaseFormatArgs: Array<String>,
	onCheckBoxCheckedChange: () -> Unit,
	onConfirmButtonClick: () -> Unit,
	onDismissButtonClick: () -> Unit,
	onDismissRequest: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismissRequest,
		title = {
			Text(text = stringResource(R.string.new_update_available))
		},
		text = {
			Column {
				Text(
					text = stringResource(
						id = R.string.update_text,
						formatArgs = lastReleaseFormatArgs
					)
				)

				Row(modifier = Modifier.paddingFromBaseline(36.dp)) {
					Checkbox(
						modifier = Modifier.padding(end = 8.dp),
						checked = checkBoxChecked,
						onCheckedChange = { onCheckBoxCheckedChange() }
					)

					Text(
						modifier = Modifier.align(Alignment.CenterVertically),
						text = stringResource(R.string.dont_show_again),
					)
				}
			}
		},
		confirmButton = {
			TextButton(onClick = onConfirmButtonClick) {
				Text(text = stringResource(R.string.download))
			}
		},
		dismissButton = {
			TextButton(onClick = onDismissButtonClick) {
				Text(text = stringResource(R.string.cancel))
			}
		}
	)
}
