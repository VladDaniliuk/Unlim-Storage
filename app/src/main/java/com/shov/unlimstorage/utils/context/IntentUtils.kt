package com.shov.unlimstorage.utils.context

import android.content.Context
import android.content.Intent

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
