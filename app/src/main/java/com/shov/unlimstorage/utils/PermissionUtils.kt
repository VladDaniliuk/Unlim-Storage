package com.shov.unlimstorage.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringDef
import androidx.compose.runtime.Composable

@Composable
fun rememberRequestMultiplePermissionsResult(
	@Permission vararg permissionsId: String, onAllowed: () -> Unit, onDenied: () -> Unit = {}
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
	return rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	) { permissions: Map<String, @JvmSuppressWildcards Boolean>? ->
		permissionsId.forEach { permissionId ->
			if (permissions?.get(permissionId) != true) {
				onDenied()
				return@rememberLauncherForActivityResult
			}
		}

		onAllowed()
	}
}
/**
 * Returns true if the app has required permissions.
 * @sample com.shov.unlimstorage.views.files.fileInfo.FileInfoScreen
 */
fun Context.checkMultiplePermissions(@Permission vararg permissionsId: String): Boolean =
	permissionsId.map { permission ->
		checkSelfPermission(permission)
	}.any { permissionResult ->
		permissionResult == PackageManager.PERMISSION_DENIED
	}.not()

@StringDef(
	Manifest.permission.READ_EXTERNAL_STORAGE,
	Manifest.permission.WRITE_EXTERNAL_STORAGE
)
annotation class Permission
