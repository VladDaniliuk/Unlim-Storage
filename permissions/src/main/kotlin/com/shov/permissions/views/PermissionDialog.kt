package com.shov.permissions.views

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun PermissionDialog(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
		PermissionDialogAllSdk(onDismissRequest, onHasAccess)
	} else onHasAccess()
}

@Composable
private fun PermissionDialogAllSdk(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	if (
		(LocalContext.current.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
				PackageManager.PERMISSION_GRANTED)
		and (LocalContext.current.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
				PackageManager.PERMISSION_GRANTED)
	) {
		onHasAccess()
	} else {
		onDismissRequest()
		(LocalContext.current as Activity).requestPermissions(
			arrayOf(
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.READ_EXTERNAL_STORAGE
			), 10
		)
	}
}
