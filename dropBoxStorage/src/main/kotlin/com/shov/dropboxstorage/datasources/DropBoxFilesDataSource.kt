package com.shov.dropboxstorage.datasources

import android.content.Context
import com.dropbox.core.BadRequestException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.RateLimitException
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.shov.coremodels.models.ItemType
import com.shov.dropboxstorage.converters.toStoreItem
import com.shov.dropboxstorage.converters.toStoreMetadataItem
import com.shov.dropboxstorage.utils.uploadFile
import com.shov.dropboxstorage.values.CLIENT_IDENTIFIER
import com.shov.dropboxstorage.values.DROPBOX_CREDENTIAL
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.storage.FilesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class DropBoxFilesDataSource @Inject constructor(
	private val preferences: PreferencesDataSource,
	@ApplicationContext val context: Context
) : FilesDataSource {
	override fun getFiles(folderId: String?) = try {
		dbxUserFilesRequests()?.listFolder(folderId ?: "")
			?.entries
			?.map { dropBoxItem ->
				dropBoxItem.toStoreItem(folderId) { context.getString(second, first) }
			}
			?.toList() ?: emptyList()
	} catch (e: RateLimitException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
	}

	override fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		dbxUserFilesRequests()?.uploadFile(folderId, name, inputStream)
	}

	override fun getFileMetadata(id: String, type: ItemType) = try {
		dbxUserFilesRequests()?.getMetadata(id)?.toStoreMetadataItem()
	} catch (e: RateLimitException) {
		null
	} catch (e: IllegalArgumentException) {
		null
	}

	override fun downloadFile(
		id: String,
		name: String,
		size: Long,
		file: File,
		setPercents: (Float, String) -> Unit,
		onStart: () -> Unit,
		onError: () -> Unit
	) {
		try {
			dbxUserFilesRequests()?.let { dbxFile ->
				dbxFile.download(dbxFile.getMetadata(id).pathLower)
					.download(FileOutputStream(file)) {
						setPercents(it.toFloat() / size.toFloat(), name)
					}

				onStart()
			} ?: onError()
		} catch (e: RateLimitException) {
			onError()
		} catch (e: IllegalArgumentException) {
			onError()
		}
	}

	override fun createFolder(folderId: String?, folderName: String) = try {
		dbxUserFilesRequests()?.createFolderV2("${getPath(folderId)}/$folderName", true)

		true
	} catch (e: BadRequestException) {
		false
	}

	private fun dbxUserFilesRequests(): DbxUserFilesRequests? {
		val dropBoxCredential by preferences.getPref(DROPBOX_CREDENTIAL, "")

		return if (dropBoxCredential.isNotEmpty()) DbxClientV2(
			DbxRequestConfig(CLIENT_IDENTIFIER),
			DbxCredential.Reader.readFully(dropBoxCredential)
		).files() else null
	}

	private fun getPath(id: String?) =
		if (id.isNullOrEmpty()) "" else dbxUserFilesRequests()?.getMetadata(id)?.pathLower
}
