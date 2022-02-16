package com.shov.unlimstorage.utils.context

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.R
import java.io.File

fun Context.installFile(file: File) {
	val uri = FileProvider.getUriForFile(
		this,
		BuildConfig.APPLICATION_ID + getString(R.string.provider),
		file
	)

	val install = Intent(Intent.ACTION_VIEW).apply {
		flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
				Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
		putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
		data = uri
	}

	startActivity(install)
}

fun Context.share(text: String) {
	startActivity(
		Intent.createChooser(
			Intent().apply {
				action = Intent.ACTION_SEND
				putExtra(Intent.EXTRA_TEXT, text)
				type = "text/*"
			},
			null
		)
	)
}
