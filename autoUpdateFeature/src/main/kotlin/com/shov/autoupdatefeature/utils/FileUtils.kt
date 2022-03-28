package com.shov.autoupdatefeature.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
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
