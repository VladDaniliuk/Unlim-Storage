package com.shov.unlimstorage.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment

fun DownloadManager.Request.setDownloadsDirectory(
	context: Context,
	name: String
): DownloadManager.Request {
	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
		setDestinationInExternalFilesDir(
			context,
			Environment.DIRECTORY_DOWNLOADS,
			name
		)
	} else {
		setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
	}

	return this
}