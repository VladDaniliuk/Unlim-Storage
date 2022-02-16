package com.shov.unlimstorage.models.repositories

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.database.getIntOrNull
import androidx.core.net.toUri
import com.shov.unlimstorage.utils.context.installFile
import com.shov.unlimstorage.utils.getPercent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface DownloadRepository {
	fun downloadFile(link: Uri, name: String): Long?
	suspend fun checkDownload(id: Long, name: String, onCheck: (Float, String) -> Unit)
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
		context.installFile(file)
	}

	override suspend fun checkDownload(id: Long, name: String, onCheck: (Float, String) -> Unit) {
		var finishDownload = false

		while (!finishDownload) {
			delay(100)

			getDownloadManagerService()?.query(DownloadManager.Query().setFilterById(id))
				?.let { cursor ->
					if (cursor.moveToFirst()) {
						when (cursor.getIntOrNull(
							cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
						)) {
							DownloadManager.STATUS_RUNNING -> onCheck(cursor.getPercent(), name)
							DownloadManager.STATUS_SUCCESSFUL -> {
								onCheck(0f, name)
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
							else -> {
								onCheck(0f, name)
								finishDownload = true
							}
						}
					} else {
						onCheck(0f, name)
						finishDownload = true
					}
				}
		}
	}

	override fun dismissDownloading(id: Long, onCheck: (Float) -> Unit) {
		getDownloadManagerService()?.remove(id)
		onCheck(0f)
	}

	private fun getDownloadManagerService() = context.getSystemService(DownloadManager::class.java)
}
