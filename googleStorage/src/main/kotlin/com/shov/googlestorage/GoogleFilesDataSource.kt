package com.shov.googlestorage

import android.content.Context
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

	override fun getDownloadLink(id: String): String =
		"https://www.googleapis.com/drive/v3/files/$id?alt=media"

	override fun getHeaders(id: String): List<Pair<String, String>> = listOf(
		"Authorization" to "Bearer ${
			GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE))
				.setSelectedAccount(GoogleSignIn.getLastSignedInAccount(context)?.account)
				.token
		}"
	)//TODO transform google documents to normal format https://developers.google.com/drive/api/v3/reference/files/export?apix_params=%7B%22fileId%22%3A%221mQxntfrS5h2oTu2YO36Dyep7kY9qwtRCrU0gjmZL6n4%22%2C%22mimeType%22%3A%22application%2Fvnd.openxmlformats-officedocument.wordprocessingml.document%22%7D#try-it

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

	override suspend fun renameFile(id: String, name: String): Boolean {
		getGoogleFiles().update(
			id,
			GoogleFile().setName(name)
		).execute()
		return true
	}
}
