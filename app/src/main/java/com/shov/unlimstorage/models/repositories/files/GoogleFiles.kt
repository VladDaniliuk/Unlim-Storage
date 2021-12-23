package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.converters.toStoreItem
import com.shov.unlimstorage.values.GOOGLE_FIELDS
import com.shov.unlimstorage.values.GOOGLE_METADATA
import com.shov.unlimstorage.values.getGoogleQ
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import com.google.api.services.drive.model.File as GoogleFile

class GoogleFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun createFolder(folderName: String): Boolean {
		getGoogleFiles().create(
			GoogleFile().apply {
				name = folderName
				mimeType = "application/vnd.google-apps.folder"
			}
		).execute()
		return true
	}

	override fun downloadFile(
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	) {
		val f = File(File("/storage/emulated/0/Download"), name)
		f.createNewFile()
		if (f.exists()) {
			val fos = FileOutputStream(f)
			val request = getGoogleFiles().get(id)
			request.executeMediaAndDownloadTo(fos)//TODO GOOGLE PERCENTS
		}
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
		getGoogleFiles().list().apply {                 //get Files.List
			fields = GOOGLE_FIELDS                      //requests fields(id,name,etc.)
			q = getGoogleQ(folderId)                    //sorting (add folder, remove trashed)
		}.execute().files.map { googleDriveItem ->
			googleDriveItem.toStoreItem(folderId)
		}.toList()
	} catch (e: GoogleJsonResponseException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
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
