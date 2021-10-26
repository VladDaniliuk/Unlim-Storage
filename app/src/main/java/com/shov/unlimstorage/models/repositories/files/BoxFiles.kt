package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.box.androidsdk.content.*
import com.box.androidsdk.content.models.BoxSession
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.utils.converters.StoreConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.values.Box
import com.shov.unlimstorage.values.getBoxFields
import com.shov.unlimstorage.values.setItemFields
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val authorizerFactory: AuthorizerFactory,
	private val storeItemConverter: StoreConverter,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun getFiles(folderId: String?): List<StoreItem> {
		return if (checkAuth) {
			try {
				BoxApiFolder(BoxSession(context)).getItemsRequest(
					folderId ?: BoxConstants.ROOT_FOLDER_ID
				).setItemFields().send().map { boxItem ->
					storeItemConverter.run {
						boxItem.toStoreItem(parentFolder = folderId)
					}
				}.toList()
			} catch (e: BoxException) {
				emptyList()
			}
		} else {
			emptyList()
		}
	}

	override fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem? {
		return if (checkAuth) {
			storeMetadataConverter.run {
				(when (type) {
					ItemType.FILE -> BoxApiFile(BoxSession(context)).getInfoRequest(id)
						.setFields(*getBoxFields()).send()
					ItemType.FOLDER -> BoxApiFolder(BoxSession(context)).getInfoRequest(id)
						.setFields(*getBoxFields()).send()
				}).toStoreMetadata()
			}
		} else {
			null
		}
	}

	private val checkAuth: Boolean
		get() {
			BoxConfig.CLIENT_ID = Box.CLIENT_ID
			BoxConfig.CLIENT_SECRET = Box.CLIENT_SECRET

			return authorizerFactory.create(StorageType.BOX).isSuccess()
		}
}
