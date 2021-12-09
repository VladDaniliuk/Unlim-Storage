package com.shov.unlimstorage.views

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.CustomDialogContent
import com.shov.unlimstorage.ui.CustomHeaderIcon
import com.shov.unlimstorage.ui.CustomText

@Composable
fun Permission(
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

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PermissionSdkR(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	val context = LocalContext.current as Activity

	if (Environment.isExternalStorageManager()) {
		onHasAccess.invoke()
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
								context.getString(R.string.package_path, BuildConfig.APPLICATION_ID)
							)
						)
					)

					onDismissRequest()
				},
				onCompleteText = stringResource(R.string.allow)
			) {
				CustomText(
					text = stringResource(id = R.string.allow_access),
					textStyle = Typography().h6
				)
			}
		}
	}
}

@Composable
fun PermissionSdkQ(onDismissRequest: () -> Unit, onHasAccess: () -> Unit) {
	val context = LocalContext.current as Activity

	if (
		(ContextCompat.checkSelfPermission(
			context,
			READ_EXTERNAL_STORAGE
		) == PackageManager.PERMISSION_GRANTED)
			.and(
				ContextCompat.checkSelfPermission(
					context,
					WRITE_EXTERNAL_STORAGE
				) == PackageManager.PERMISSION_GRANTED
			)
	) {
		onHasAccess.invoke()
	} else {
		onDismissRequest.invoke()
		context.requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 10)
	}
}
