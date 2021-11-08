package com.shov.unlimstorage.utils

import android.app.DownloadManager
import android.database.Cursor
import androidx.core.database.getFloatOrNull

fun Cursor.getPercent() =
	getFloatOrNull(getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) ?: (0f /
			(getFloatOrNull(getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) ?: 0f))
