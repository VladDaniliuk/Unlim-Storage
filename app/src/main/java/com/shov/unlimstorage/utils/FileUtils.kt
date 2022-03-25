package com.shov.unlimstorage.utils

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.net.toFile
import java.io.File

fun Context.getDownloadsDirectory(): File {
	return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
		getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: throw Exception("No such directory")
	} else {
		Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
	}
}

fun Context.getApkVersionName(file: File): String? = packageManager
	.getPackageArchiveInfo(file.absolutePath, 0)?.versionName

fun Cursor.getFile(): File = Uri
	.parse(getString(getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))).toFile()