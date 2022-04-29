package com.shov.dropboxstorage.datasources

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.dropbox.core.BadRequestException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.RateLimitException
import com.dropbox.core.android.Auth
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

	override suspend fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
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
		name: String
	) {
		val downloadManager = context.getSystemService(DownloadManager::class.java)
		val request = DownloadManager
			.Request(Uri.parse("https://content.dropboxapi.com/2/files/download"))
			.addRequestHeader("Authorization", "Bearer ${Auth.getOAuth2Token()}")
			.addRequestHeader("Dropbox-API-Arg", "{\"path\":\"$id\"}")
			.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
			.apply {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
					setTitle(name)
					setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, name)
				} else {
					setTitle("Downloading $name")//if using in android 9 and above below -> rename file
					setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
				}
			}

		downloadManager.enqueue(request)
	}

	override fun createFolder(folderId: String?, folderName: String) =
		if (folderName.isNotEmpty()) {
			try {
				dbxUserFilesRequests()?.createFolderV2("${getPath(folderId)}/$folderName", true)

				true
			} catch (e: BadRequestException) {
				false
			}
		} else false

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
