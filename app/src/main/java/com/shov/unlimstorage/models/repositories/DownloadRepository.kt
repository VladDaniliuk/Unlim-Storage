package com.shov.unlimstorage.models.repositories

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.database.getIntOrNull
import androidx.core.net.toUri
import com.shov.unlimstorage.BuildConfig
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.getPercent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


interface DownloadRepository {
	fun downloadFile(link: Uri, name: String): Long?
	suspend fun checkDownload(id: Long, name: String, onCheck: (Float) -> Unit)
	fun dismissDownloading(id: Long, onCheck: (Float) -> Unit)
}

@Singleton
class DownloadRepositoryImpl @Inject constructor(
	@ApplicationContext val context: Context
) : DownloadRepository {
	override fun downloadFile(link: Uri, name: String): Long? {
		val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name)

		return if (file.exists()) {
			showInstalling(file)
			null
		} else {
			getDownloadManagerService()?.enqueue(
				DownloadManager.Request(link)
					.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
					.setTitle(context.packageManager.getApplicationLabel(context.applicationInfo))
					.setDestinationUri(file.toUri())
			)
		}

	}

	private fun showInstalling(file: File) {
		val contentUri = FileProvider.getUriForFile(
			context,
			BuildConfig.APPLICATION_ID + context.getString(R.string.provider),
			file
		)
		val install = Intent(Intent.ACTION_VIEW)
		install.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
				Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
		install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
		install.data = contentUri
		context.startActivity(install)
	}

	override suspend fun checkDownload(id: Long, name: String, onCheck: (Float) -> Unit) {
		var finishDownload = false

		while (!finishDownload) {
			delay(100)

			getDownloadManagerService()?.query(DownloadManager.Query().setFilterById(id))
				?.let { cursor ->
					if (cursor.moveToFirst()) {
						when (cursor.getIntOrNull(
							cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
						)) {
							DownloadManager.STATUS_RUNNING -> onCheck(cursor.getPercent())
							DownloadManager.STATUS_SUCCESSFUL -> {
								onCheck(0f)
								showInstalling(
									File(
										context.getExternalFilesDir(
											Environment.DIRECTORY_DOWNLOADS
										),
										name
									)
								)
								finishDownload = true
							}
							else -> onCheck(0f)
						}
					} else onCheck(0f)
				}
		}
	}

	override fun dismissDownloading(id: Long, onCheck: (Float) -> Unit) {
		getDownloadManagerService()?.remove(id)
		onCheck(0f)
	}

	private fun getDownloadManagerService() =
		ContextCompat.getSystemService(context, DownloadManager::class.java)
}