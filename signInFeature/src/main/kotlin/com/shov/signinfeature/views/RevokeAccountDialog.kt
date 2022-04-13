package com.shov.signinfeature.views

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.signinfeature.R
import com.shov.signinfeature.viewModels.RevokeAccountViewModel

@Composable
fun RevokeAccountDialog(
	revokeAccountViewModel: RevokeAccountViewModel = hiltViewModel(),
	onClose: () -> Unit,
) {
	RevokeAccountView(
		nameId = revokeAccountViewModel.storageType.nameId,
		onClose = onClose
	) {
		revokeAccountViewModel.signOut(onClose)
	}
}

@Composable
internal fun RevokeAccountView(@StringRes nameId: Int, onClose: () -> Unit, onConfirm: () -> Unit) {
	AlertDialog(
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text(text = stringResource(id = R.string.revoke))
			}
		},
		dismissButton = {
			TextButton(onClick = onClose) {
				Text(text = stringResource(id = android.R.string.cancel))
			}
		},
		onDismissRequest = onClose,
		text = {
			Text(text = stringResource(id = R.string.revoke_access_to, stringResource(id = nameId)))
		},
		title = { Text(text = stringResource(id = R.string.revoke_access)) }
	)
}

@Preview
@Composable
private fun RevokeAccountPreview() {
	RevokeAccountView(nameId = StorageType.GOOGLE.nameId, {}) {}
}
