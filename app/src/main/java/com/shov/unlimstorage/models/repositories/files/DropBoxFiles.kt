package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.dropbox.core.BadRequestException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.RateLimitException
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.converters.toStoreItem
import com.shov.unlimstorage.values.DROPBOX_CLIENT_IDENTIFIER
import com.shov.unlimstorage.values.DROPBOX_CREDENTIAL
import com.shov.unlimstorage.values.DROPBOX_ROOT_FOLDER
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class DropBoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun getFiles(folderId: String?) = try {
		getFiles()?.listFolder(
			folderId ?: DROPBOX_ROOT_FOLDER
		)?.entries?.map { dropBoxItem ->
			dropBoxItem.toStoreItem(parentFolder = folderId)
		}?.toList() ?: emptyList()
	} catch (e: RateLimitException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
	}

	override fun getFileMetadata(id: String, type: ItemType) = try {
		storeMetadataConverter.run {
			getFiles()?.getMetadata(id)?.toStoreMetadata()
		}
	} catch (e: RateLimitException) {
		null
	} catch (e: IllegalArgumentException) {
		null
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
			try {
				getFiles()?.let { file ->
					file.download(file.getMetadata(id).pathLower).download(fos) {
						setPercents(it.toFloat() / size.toFloat(), name)
					}
				}
			} catch (e: RateLimitException) {
			} catch (e: IllegalArgumentException) {
			}
		}
	}

	override fun createFolder(folderId: String?, folderName: String) = try {
		getFiles()?.createFolderV2("${getPath(folderId)}/$folderName", true)

		true
	} catch (e: BadRequestException) {
		false
	}

	private fun getFiles(): DbxUserFilesRequests? {
		val credentialPref by Preference(context, DROPBOX_CREDENTIAL, "")

		return if (credentialPref.isNotEmpty())
			DbxClientV2(
				DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER),
				DbxCredential.Reader.readFully(credentialPref)
			).files()
		else null
	}

	private fun getPath(id: String?) = getFiles()?.getMetadata(id)?.pathLower ?: DROPBOX_ROOT_FOLDER
}
