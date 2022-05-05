package com.shov.autoupdatefeature.data.repositories

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.core.database.getIntOrNull
import com.shov.autoupdatefeature.utils.*
import com.shov.coremodels.inheritances.DownloadManagerRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface DownloadRepository {
	fun downloadFile(link: Uri, name: String, newVersion: String): Long?
	suspend fun checkDownload(id: Long, name: String)
}

@Singleton
class DownloadRepositoryImpl @Inject constructor(
	@ApplicationContext val context: Context
) : DownloadRepository {
	override fun downloadFile(link: Uri, name: String, newVersion: String): Long? {
		val file = File(context.getDownloadsDirectory(), name)

		newVersion.compareWithOld(
			context.getApkVersionName(file),
			onNewerAction = file::delete
		)

		return if (file.exists()) {
			context.installFile(file)

			null
		} else {
			DownloadManagerRequest(context, link, name)
				.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
				.setTitle(context.packageManager.getApplicationLabel(context.applicationInfo))
				.enqueue()
		}
	}

	override suspend fun checkDownload(id: Long, name: String) {
		var finishDownload = false

		while (!finishDownload) {
			delay(100)

			context.getSystemService(DownloadManager::class.java)
				?.query(DownloadManager.Query().setFilterById(id))
				?.let { cursor ->
					if (cursor.moveToFirst()) {
						when (cursor.getIntOrNull(
							cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
						)) {
							DownloadManager.STATUS_RUNNING -> {}
							DownloadManager.STATUS_SUCCESSFUL -> {
								context.installFile(cursor.getFile())

								finishDownload = true
							}
							else -> {
								finishDownload = true
							}
						}
					} else {
						finishDownload = true
					}
				}
		}
	}
}
