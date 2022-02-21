package com.shov.unlimstorage.utils.files

import com.google.api.client.http.InputStreamContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.shov.coremodels.models.StoreItem
import com.shov.unlimstorage.values.GOOGLE_FIELDS
import com.shov.unlimstorage.values.getGoogleQ
import java.io.InputStream

fun Drive.Files.createFolder(folderName: String, parents: List<String?>) {
	create(file(folderName, parents, true)).execute()
}

fun Drive.Files.uploadFile(name: String, parents: List<String?>, inputStream: InputStream) {
	create(file(name, parents), InputStreamContent(null, inputStream)).execute()
}

private fun file(name: String, parents: List<String?> = emptyList(), isFolder: Boolean = false) =
	File().apply {
		this.name = name
		this.parents = parents
		if (isFolder) mimeType = "application/vnd.google-apps.folder"
	}

fun Drive.Files.getFileList(folderId: String?, toStoreItem: (File) -> StoreItem) = list()
	.setFields(GOOGLE_FIELDS)
	.setQ(getGoogleQ(folderId))
	.execute()
	.files.map(toStoreItem)
	.toList()
