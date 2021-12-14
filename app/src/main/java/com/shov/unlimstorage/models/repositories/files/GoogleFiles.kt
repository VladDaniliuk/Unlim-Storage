package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.utils.converters.StoreConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.values.GOOGLE_FIELDS
import com.shov.unlimstorage.values.GOOGLE_METADATA
import com.shov.unlimstorage.values.getGoogleQ
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val storeItemConverter: StoreConverter,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun getFiles(folderId: String?): List<StoreItem> {
		return try {
			getGoogleFiles().list().apply {                 //get Files.List
				fields = GOOGLE_FIELDS                      //requests fields(id,name,etc.)
				q = getGoogleQ(folderId)                    //sorting (add folder, remove trashed)
			}.execute().files.map { googleDriveItem ->
				storeItemConverter.run {
					googleDriveItem.toStoreItem(folderId)
				}
			}.toList()
		} catch (e: GoogleJsonResponseException) {
			emptyList()
		} catch (e: IllegalArgumentException) {
			emptyList()
		}
	}

	override fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem? {
		return try {
			getGoogleFiles().get(id).apply {
				fields = GOOGLE_METADATA
			}.execute().let { storeMetadataConverter.run { it.toStoreMetadata() } }
		} catch (e: GoogleJsonResponseException) {
			null
		} catch (e: IllegalArgumentException) {
			null
		}
	}

	private fun getGoogleFiles(): Drive.Files {
		GoogleSignIn.getLastSignedInAccount(context)?.let { googleAccount ->
			return Drive.Builder(
				AndroidHttp.newCompatibleTransport(),
				JacksonFactory.getDefaultInstance(),
				GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE))
					.apply { selectedAccount = googleAccount.account })
				.setApplicationName(context.getString(R.string.app_name)).build().Files()
		} ?: throw IllegalArgumentException()
	}
}
