package com.shov.coremodels.inheritances

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.IntDef

class DownloadManagerRequest(
	val context: Context, uri: Uri, name: String
) : DownloadManager.Request(uri) {
	constructor(context: Context, uri: String, name: String) : this(context, Uri.parse(uri), name)

	/**
	 * Set standard parameters, such as the title, notification visibility and destination.
	 */
	init {
		setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

		setTitle(name)

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) setDestinationInExternalFilesDir(
			context,
			Environment.DIRECTORY_DOWNLOADS,
			name
		) else setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
	}

	/**
	 * @param visibility type of notification to be shown to the user while the download is in progress. Default is [VISIBILITY_VISIBLE_NOTIFY_COMPLETED].
	 */
	override fun setNotificationVisibility(
		@NotificationVisibility visibility: Int
	): DownloadManagerRequest {
		super.setNotificationVisibility(visibility)

		return this
	}

	/**
	 * @param headers list of HTTP headers to be sent along with the download request.
	 */
	fun addRequestHeaders(headers: List<Pair<String, String>>): DownloadManagerRequest {
		headers.forEach {
			addRequestHeader(it.first, it.second)
		}

		return this
	}

	/**
	 * @param name title of the download notification. If using in android 9 and above -> using not name rename file
	 */
	override fun setTitle(name: CharSequence): DownloadManagerRequest {
		super.setTitle(
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) name else "Downloading $name"
		)

		return this
	}

	fun enqueue(): Long = context.getSystemService(DownloadManager::class.java).enqueue(this)
}

@IntDef(
	DownloadManager.Request.VISIBILITY_VISIBLE, VISIBILITY_VISIBLE_NOTIFY_COMPLETED,
	DownloadManager.Request.VISIBILITY_HIDDEN
)
@Retention(AnnotationRetention.SOURCE)
annotation class NotificationVisibility
