package com.shov.autoupdatefeature.data.repositories

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.core.database.getIntOrNull
import com.shov.autoupdatefeature.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface DownloadRepository {
	fun downloadFile(link: Uri, name: String, newVersion: String): Long?
	suspend fun checkDownload(id: Long, name: String, onCheck: (String) -> Unit)
	fun dismissDownloading(id: Long)
}

@Singleton
class DownloadRepositoryImpl @Inject constructor(
	@ApplicationContext val context: Context
) : DownloadRepository {
	override fun downloadFile(link: Uri, name: String, newVersion: String): Long? {
		val file = File(context.getDownloadsDirectory(), name)

		newVersion.compareWithOld(
			context.getApkVersionName(file),
			onNewerAction = { file.delete() }
		)

		return if (file.exists()) {
			context.installFile(file)

			null
		} else {
			getDownloadManagerService()?.enqueue(
				DownloadManager.Request(link)
					.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
					.setTitle(context.packageManager.getApplicationLabel(context.applicationInfo))
					.setDownloadsDirectory(context, name)
			)
		}
	}

	override suspend fun checkDownload(id: Long, name: String, onCheck: (String) -> Unit) {
		var finishDownload = false

		while (!finishDownload) {
			delay(100)

			getDownloadManagerService()?.query(DownloadManager.Query().setFilterById(id))
				?.let { cursor ->
					if (cursor.moveToFirst()) {
						when (cursor.getIntOrNull(
							cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
						)) {
							DownloadManager.STATUS_RUNNING -> onCheck(name)
							DownloadManager.STATUS_SUCCESSFUL -> {
								onCheck("")

								context.installFile(cursor.getFile())

								finishDownload = true
							}
							else -> {
								onCheck("")
								finishDownload = true
							}
						}
					} else {
						onCheck("")
						finishDownload = true
					}
				}
		}
	}

	override fun dismissDownloading(id: Long) {
		getDownloadManagerService()?.remove(id)
	}

	private fun getDownloadManagerService() = context.getSystemService(DownloadManager::class.java)
}
