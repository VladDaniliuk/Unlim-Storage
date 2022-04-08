package com.shov.boxstorage

import android.content.Context
import com.box.androidsdk.content.*
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.boxstorage.converters.getFileMetadata
import com.shov.boxstorage.converters.toStoreItem
import com.shov.coremodels.models.ItemType
import com.shov.storage.FilesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
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
		onDownload: (String) -> Unit,
		onError: () -> Unit
	) {
		if (checkAuth) {
			BoxApiFile(BoxSession(context))
				.getDownloadRequest(FileOutputStream(file), id)
				.setProgressListener { numBytes, totalBytes ->
					if (numBytes == totalBytes) {
						onDownload("")
					} else {
						onDownload(name)
					}
				}
				.send()
		} else onError()
	}

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
				.map { boxItem ->
					boxItem.toStoreItem(folderId) {
						context.getString(second, first)
					}
				}.toList()
		} catch (e: BoxException) {
			emptyList()
		}
	} else emptyList()

	override fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		BoxApiFile(BoxSession(context)).getUploadRequest(
			inputStream,
			name,
			folderId ?: BoxConstants.ROOT_FOLDER_ID
		).send()
	}
}
