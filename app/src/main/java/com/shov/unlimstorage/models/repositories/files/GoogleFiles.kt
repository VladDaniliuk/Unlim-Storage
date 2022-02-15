package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.InputStreamContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.shov.unlimstorage.utils.converters.toStoreItem
import com.shov.unlimstorage.values.GOOGLE_FIELDS
import com.google.api.services.drive.DriveScopes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.files.createFile
import com.shov.unlimstorage.utils.files.createFolder
import com.shov.unlimstorage.utils.files.getFileList
import com.shov.unlimstorage.utils.files.uploadFile
import com.shov.unlimstorage.values.DOWNLOAD_PATH
import com.shov.unlimstorage.values.GOOGLE_METADATA
import com.shov.unlimstorage.values.getGoogleQ
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import com.google.api.services.drive.model.File as GoogleFile

class GoogleFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun createFolder(folderId: String?, folderName: String): Boolean {
		getGoogleFiles().createFolder(folderName, listOf(folderId))
		return true
	}

	override fun downloadFile(
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	) {
		File(DOWNLOAD_PATH).createFile(
			name = name,
			onCreate = {
				getGoogleFiles().get(id).executeMediaAndDownloadTo(FileOutputStream(this))
			}//TODO onExist onError
		)//TODO GOOGLE PERCENTS
	}

	override fun getFileMetadata(id: String, type: ItemType) = try {
		getGoogleFiles().get(id).apply {
			fields = GOOGLE_METADATA
		}.execute().let { storeMetadataConverter.run { it.toStoreMetadata() } }
	} catch (e: GoogleJsonResponseException) {
		null
	} catch (e: IllegalArgumentException) {
		null
	}

	override fun getFiles(folderId: String?) = try {
		getGoogleFiles().getFileList(folderId) { file ->
			file.toStoreItem(folderId)
		}
	} catch (e: GoogleJsonResponseException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
	}

	override fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		getGoogleFiles().uploadFile(name, listOf(folderId), inputStream)
	}

	private fun getGoogleFiles() = GoogleSignIn.getLastSignedInAccount(context)?.run {
		Drive.Builder(
			NetHttpTransport(),
			JacksonFactory.getDefaultInstance(),
			GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE))
				.apply { selectedAccount = account }
		).setApplicationName(context.getString(R.string.app_name)).build().Files()
	} ?: throw IllegalArgumentException()
}
