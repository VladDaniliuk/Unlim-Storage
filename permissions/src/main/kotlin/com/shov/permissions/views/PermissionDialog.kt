package com.shov.permissions.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.shov.coreui.ui.dialogs.CustomDialogContent
import com.shov.coreui.ui.dialogs.CustomHeaderIcon
import com.shov.permissions.R

@Composable
fun PermissionDialog(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		PermissionDialogR(onDismissRequest, onHasAccess)
	} else {
		PermissionDialogAllSdk(onDismissRequest, onHasAccess)
	}
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
private fun PermissionDialogR(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	val context = LocalContext.current as Activity

	if (Environment.isExternalStorageManager()) {
		onHasAccess()
	} else {
		Dialog(onDismissRequest = onDismissRequest) {
			CustomDialogContent(
				header = { CustomHeaderIcon(icon = Icons.Rounded.Folder) },
				onCancelRequest = onDismissRequest,
				onCancelText = stringResource(R.string.deny),
				onCompleteRequest = {
					context.startActivity(
						Intent(
							Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
							Uri.parse(
								context.getString(R.string.package_path, context.packageName)
							)
						)
					)

					onDismissRequest()
				},
				onCompleteText = stringResource(R.string.allow)
			)
		}
	}
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
