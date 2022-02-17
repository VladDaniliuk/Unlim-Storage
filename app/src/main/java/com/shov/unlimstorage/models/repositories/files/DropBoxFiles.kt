package com.shov.unlimstorage.models.repositories.files

import com.dropbox.core.BadRequestException
import com.dropbox.core.RateLimitException
import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.dropbox.core.v2.files.WriteMode
import com.shov.coremodels.ItemType
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.files.createDbxUserFilesRequests
import com.shov.unlimstorage.utils.files.createFile
import com.shov.unlimstorage.values.DOWNLOAD_PATH
import com.shov.unlimstorage.values.DROPBOX_CREDENTIAL
import com.shov.unlimstorage.values.DROPBOX_ROOT_FOLDER
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class DropBoxFiles @Inject constructor(
	private val preference: PreferenceRepository,
	private val storeMetadataConverter: StoreMetadataConverter,
	private val storeItemConverter: StoreItemConverter,
) : FilesInteractor {
	override fun getFiles(folderId: String?) = try {
		dbxUserFilesRequests()?.listFolder(folderId ?: DROPBOX_ROOT_FOLDER)
			?.entries
			?.map { dropBoxItem -> storeItemConverter.run { dropBoxItem.toStoreItem(folderId) } }
			?.toList() ?: emptyList()
	} catch (e: RateLimitException) {
		emptyList()
	} catch (e: IllegalArgumentException) {
		emptyList()
	}

	override fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		dbxUserFilesRequests()?.uploadBuilder("${getPath(folderId)}/$name")
			?.withMode(WriteMode.ADD)
			?.uploadAndFinish(inputStream)
	}

	override fun getFileMetadata(id: String, type: ItemType) = try {
		storeMetadataConverter.run {
			dbxUserFilesRequests()?.getMetadata(id)?.toStoreMetadata()
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
		File(DOWNLOAD_PATH).createFile(
			name = name,
			onCreate = {
				try {
					dbxUserFilesRequests()?.let { file ->
						file.download(file.getMetadata(id).pathLower)
							.download(FileOutputStream(this)) {
								setPercents(it.toFloat() / size.toFloat(), name)
							}
					}
				} catch (e: RateLimitException) {
				} catch (e: IllegalArgumentException) {
				}
			}
		)
	}

	override fun createFolder(folderId: String?, folderName: String) = try {
		dbxUserFilesRequests()?.createFolderV2("${getPath(folderId)}/$folderName", true)

		true
	} catch (e: BadRequestException) {
		false
	}

	private fun dbxUserFilesRequests(): DbxUserFilesRequests? {
		val dropBoxCredential by preference.getPref(DROPBOX_CREDENTIAL, "")

		return createDbxUserFilesRequests(dropBoxCredential)
	}

	private fun getPath(id: String?) =
		if (id.isNullOrEmpty()) DROPBOX_ROOT_FOLDER
		else dbxUserFilesRequests()?.getMetadata(id)?.pathLower
}
