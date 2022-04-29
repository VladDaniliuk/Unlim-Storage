package com.shov.boxstorage

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.box.androidsdk.content.*
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.boxstorage.converters.getFileMetadata
import com.shov.boxstorage.converters.toStoreItem
import com.shov.coremodels.models.ItemType
import com.shov.storage.FilesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class BoxFilesDataSource @Inject constructor(
	@ApplicationContext val context: Context
) : FilesDataSource {
	private val checkAuth: Boolean
		get() {
			BoxConfig.CLIENT_ID = BOX_CLIENT_ID
			BoxConfig.CLIENT_SECRET = BOX_CLIENT_SECRET

			return BoxSession(context).user?.status == BoxUser.Status.ACTIVE
		}

	override fun createFolder(folderId: String?, folderName: String) = if (checkAuth) {
		try {
			BoxApiFolder(BoxSession(context)).getCreateRequest(
				folderId ?: BoxConstants.ROOT_FOLDER_ID,
				folderName
			).send()

			true
		} catch (e: BoxException) {
			false
		}
	} else false

	override fun downloadFile(
		id: String,
		name: String,
		file: File,
		onError: () -> Unit
	) {
		val downloadManager = context.getSystemService(DownloadManager::class.java)
		val request = DownloadManager
			.Request(Uri.parse("https://api.box.com/2.0/files/$id/content"))
			.addRequestHeader(
				"Authorization", "Bearer ${BoxSession(context).authInfo.accessToken()}"
			)
			.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
			.setTitle("Downloading $name")
			.apply {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
					setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, name)
				} else {
					setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
				}
			}

		downloadManager.enqueue(request)
	}//TODO need mediaScanner

	override fun getFileMetadata(id: String, type: ItemType) = if (checkAuth) {
		when (type) {
			ItemType.FILE -> BoxApiFile(BoxSession(context))
			ItemType.FOLDER -> BoxApiFolder(BoxSession(context))
		}.getFileMetadata(id, type)
	} else null

	override fun getFiles(folderId: String?) = if (checkAuth) {
		try {
			BoxApiFolder(BoxSession(context))
				.getItemsRequest(folderId ?: BoxConstants.ROOT_FOLDER_ID)
				.setFields("size", "name", "parent").send()
				.filter { (it.type == "file") or (it.type == "folder") }
				.map { boxItem ->
					boxItem.toStoreItem(folderId) {
						context.getString(second, first)
					}
				}.toList()
		} catch (e: BoxException) {
			emptyList()
		}
	} else emptyList()

	override suspend fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		BoxApiFile(BoxSession(context)).getUploadRequest(
			inputStream,
			name,
			folderId ?: BoxConstants.ROOT_FOLDER_ID
		).send()
	}
}
