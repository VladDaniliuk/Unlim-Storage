package com.shov.unlimstorage.views.permissions

import android.Manifest
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
import com.shov.unlimstorage.ui.CustomText
import com.shov.unlimstorage.ui.dialogs.CustomDialogContent
import com.shov.unlimstorage.ui.dialogs.CustomHeaderIcon

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PermissionSdkR(
	context: Activity = LocalContext.current as Activity,
	onDismissRequest: () -> Unit,
	onHasAccess: () -> Unit
) {
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
fun PermissionSdkQ(
	context: Activity = LocalContext.current as Activity,
	onDismissRequest: () -> Unit,
	onHasAccess: () -> Unit
) {
	if (
		(ContextCompat.checkSelfPermission(
			context,
			Manifest.permission.READ_EXTERNAL_STORAGE
		) == PackageManager.PERMISSION_GRANTED)
			.and(
				ContextCompat.checkSelfPermission(
					context,
					Manifest.permission.WRITE_EXTERNAL_STORAGE
				) == PackageManager.PERMISSION_GRANTED
			)
	) {
		onHasAccess.invoke()
	} else {
		onDismissRequest.invoke()
		context.requestPermissions(arrayOf(
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE
		), 10)
	}
}
