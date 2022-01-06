package com.shov.unlimstorage.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns


fun ContentResolver.getName(uri: Uri): String {
	var name = ""
	val cursor = query(uri, null, null, null, null)
	cursor?.use { nameCursor ->
		nameCursor.moveToFirst()
		name = nameCursor.getString(nameCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
	}

	return name
}
