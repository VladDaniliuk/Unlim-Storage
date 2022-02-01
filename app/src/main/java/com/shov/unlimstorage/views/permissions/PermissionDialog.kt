package com.shov.unlimstorage.views.permissions

import android.os.Build
import androidx.compose.runtime.Composable

@Composable
fun PermissionDialog(
	onDismissRequest: () -> Unit,
	onHasAccess: () -> Unit
) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		PermissionSdkR(
			onDismissRequest = onDismissRequest,
			onHasAccess = onHasAccess
		)
	} else {
		PermissionSdkQ(
			onDismissRequest = onDismissRequest,
			onHasAccess = onHasAccess
		)
	}
}
