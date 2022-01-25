package com.shov.unlimstorage.utils

import android.content.Context
import android.content.pm.PackageManager

fun Context.checkPermissionList(vararg permissions: String): Boolean {
	var finalPermission = PackageManager.PERMISSION_GRANTED
	permissions.forEach { permission ->
		finalPermission = finalPermission or checkSelfPermission(permission)
	}

	return finalPermission == PackageManager.PERMISSION_GRANTED
}

fun Map<String, Boolean>.checkPermissionList() = values.find { it.not() } == null