package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.box.androidsdk.content.BoxApiFile
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.BoxConstants
import com.box.androidsdk.content.BoxException
import com.box.androidsdk.content.models.BoxSession
import com.shov.coremodels.ItemType
import com.shov.coremodels.StorageType
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.files.createFile
import com.shov.unlimstorage.values.DOWNLOAD_PATH
import com.shov.unlimstorage.values.getBoxFields
import com.shov.unlimstorage.values.setItemFields
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class BoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val authorizerFactory: AuthorizerFactory,
	private val storeMetadataConverter: StoreMetadataConverter,
	private val storeItemConverter: StoreItemConverter
) : FilesInteractor {
	private val checkAuth: Boolean
		get() = authorizerFactory.create(StorageType.BOX).isSuccess()

	override fun createFolder(folderId: String?, folderName: String) = try {
		if (checkAuth) {
			BoxApiFolder(BoxSession(context)).getCreateRequest(
				folderId ?: BoxConstants.ROOT_FOLDER_ID,
				folderName
			).send()

			true
		} else false
	} catch (e: BoxException) {
		false
	}

	override fun downloadFile(
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	) {
		if (checkAuth) {
			File(DOWNLOAD_PATH).createFile(
				name = name,
				onCreate = {
					BoxApiFile(BoxSession(context))
						.getDownloadRequest(FileOutputStream(this), id)
						.setProgressListener { numBytes, _ ->
							setPercents((numBytes.toFloat() / size.toFloat()), name)
						}.send()
				}
			)//TODO onExist and onError
		}
	}

	override fun getFileMetadata(id: String, type: ItemType) = if (checkAuth) {
		storeMetadataConverter.run {
			(when (type) {
				ItemType.FILE -> BoxApiFile(BoxSession(context)).getInfoRequest(id)
					.setFields(*getBoxFields()).send()
				ItemType.FOLDER -> BoxApiFolder(BoxSession(context)).getInfoRequest(id)
					.setFields(*getBoxFields()).send()
			}).toStoreMetadata()
		}
	} else null

	override fun getFiles(folderId: String?) = if (checkAuth) {
		try {
			BoxApiFolder(BoxSession(context))
				.getItemsRequest(folderId ?: BoxConstants.ROOT_FOLDER_ID)
				.setItemFields().send()
				.map { boxItem ->
					storeItemConverter.run { boxItem.toStoreItem(folderId) }
				}.toList()
		} catch (e: BoxException) {
			emptyList()
		}
	} else emptyList()

	override fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
		BoxApiFile(BoxSession(context)).getUploadRequest(
			inputStream,
			name,
			folderId ?: BoxConstants.ROOT_FOLDER_ID
		).send()
	}
}
