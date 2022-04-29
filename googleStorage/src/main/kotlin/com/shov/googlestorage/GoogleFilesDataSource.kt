package com.shov.googlestorage

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.InputStreamContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.shov.coremodels.R
import com.shov.coremodels.models.ItemType
import com.shov.googlestorage.converters.toStoreItem
import com.shov.googlestorage.converters.toStoreMetadataItem
import com.shov.storage.FilesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import com.google.api.services.drive.model.File as GoogleFile

class GoogleFilesDataSource @Inject constructor(
	@ApplicationContext val context: Context
) : FilesDataSource {
	override fun createFolder(folderId: String?, folderName: String): Boolean {
		getGoogleFiles().create(
			GoogleFile().setName(folderName)
				.setParents(listOf(folderId))
				.setMimeType("application/vnd.google-apps.folder")
		).execute()
		return true
	}

	override fun downloadFile(
		id: String,
		name: String,
		file: File,
		onError: () -> Unit
	) {
		val downloadManager = context.getSystemService(DownloadManager::class.java)
		val request = DownloadManager
			.Request(Uri.parse("https://www.googleapis.com/drive/v3/files/$id?alt=media"))
			.addRequestHeader(
				"Authorization", "Bearer ${
					GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE))
						.setSelectedAccount(GoogleSignIn.getLastSignedInAccount(context)?.account)
						.token
				}"
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
	}

	override fun getFileMetadata(id: String, type: ItemType) = try {
		getGoogleFiles().get(id)
			.setFields(
				"id,name,mimeType,description,permissions,shared,starred,version,webViewLink" +
						",createdTime,modifiedTime,owners,size"
			)
			.execute()
			.toStoreMetadataItem(type)
	} catch (e: GoogleJsonResponseException) {
		null
	} catch (e: IllegalArgumentException) {
		null
	}

	override fun getFiles(folderId: String?) = try {
		getGoogleFiles().list()
			.setFields("files(id,size,name,mimeType,parents)")
			.setQ("parents = '${folderId ?: "root"}' and trashed = false")
			.execute()
			.files.map { file ->
				file.toStoreItem(folderId) { context.getString(second, first) }
			}
	} catch (e: GoogleJsonResponseException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
	}

	override suspend fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		getGoogleFiles().create(
			GoogleFile().setName(name)
				.setParents(listOf(folderId)),
			InputStreamContent(null, inputStream)
		).execute()
	}

	private fun getGoogleFiles() = GoogleSignIn.getLastSignedInAccount(context)?.run {
		Drive.Builder(
			NetHttpTransport(),
			JacksonFactory.getDefaultInstance(),
			GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE))
				.setSelectedAccount(account)
		).setApplicationName(context.getString(R.string.app_name))
			.build()
			.Files()
	} ?: throw IllegalArgumentException()
}
