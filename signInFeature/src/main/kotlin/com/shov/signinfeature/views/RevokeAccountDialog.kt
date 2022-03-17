package com.shov.signinfeature.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.shov.coremodels.models.StorageType
import com.shov.signinfeature.R

@Composable
fun RevokeAccountDialog(
	nameId: Int,
	onDismiss: () -> Unit,
	onRevoke: () -> Unit,
) {
	AlertDialog(
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text(text = stringResource(id = android.R.string.cancel))
			}
		},
		confirmButton = {
			TextButton(onClick = {
				onRevoke()
			}) {
				Text(text = stringResource(id = R.string.revoke))
			}
		},
		onDismissRequest = onDismiss,
		properties = DialogProperties(),
		text = {
			Text(
				text = stringResource(
					id = R.string.revoke_access_to,
					stringResource(id = nameId)
				)
			)
		},
		title = { Text(text = stringResource(id = R.string.revoke_access)) },
	)
}

@Preview
@Composable
fun RevokeAccountPreview() {
	RevokeAccountDialog(
		nameId = StorageType.BOX.nameId,
		onDismiss = {}
	) {}
}
