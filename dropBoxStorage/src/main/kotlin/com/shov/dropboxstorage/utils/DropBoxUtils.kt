package com.shov.dropboxstorage.utils

import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.dropbox.core.v2.files.WriteMode
import java.io.InputStream

internal fun DbxUserFilesRequests.uploadFile(
	folderId: String?,
	fileName: String,
	inputStream: InputStream
) {
	uploadBuilder("${folderId?.let(::getMetadata)?.pathLower ?: ""}/$fileName")
		.withMode(WriteMode.ADD)
		.uploadAndFinish(inputStream)
}
