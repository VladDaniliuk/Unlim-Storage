package com.shov.unlimstorage.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties

@Composable
fun Dialog(
	addableText: Int? = null,
	text: Int,
	title: Int,
	onDismissRequest: () -> Unit,
	onDismissButtonClick: () -> Unit,
	dismissButtonText: Int,
	onConfirmButtonClick: () -> Unit,
	confirmButtonText: Int
) {
	AlertDialog(
		dismissButton = {
			TextButton(onClick = onDismissButtonClick) {
				Text(text = stringResource(id = dismissButtonText))
			}
		},
		confirmButton = {
			TextButton(onClick = onConfirmButtonClick) {
				Text(text = stringResource(id = confirmButtonText))
			}
		},
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(),
		text = {
			Text(text = addableText?.let { addableText ->
				stringResource(id = text, stringResource(id = addableText))
			} ?: stringResource(id = text))
		},
		title = { Text(text = stringResource(id = title)) },
	)
}
