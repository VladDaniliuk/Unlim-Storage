package com.shov.autoupdatefeature.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import java.io.File

fun Context.installFile(file: File) {
	val install = Intent(Intent.ACTION_VIEW).apply {
		flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
				Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
		putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
		data = FileProvider.getUriForFile(this@installFile, "$packageName.provider", file)
	}

	startActivity(install)
}

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
