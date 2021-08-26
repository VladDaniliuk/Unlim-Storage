package com.shov.unlimstorage.models.filesRepository

import android.content.Context
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.BoxConstants
import com.box.androidsdk.content.BoxException
import com.box.androidsdk.content.models.BoxSession
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.models.signInModels.AuthorizerFactory
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.values.Box
import com.shov.unlimstorage.values.NAME
import com.shov.unlimstorage.values.PARENT
import com.shov.unlimstorage.values.SIZE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val authorizerFactory: AuthorizerFactory,
	private val storeItemConverter: StoreItemConverter
) : FilesInteractor {
	override fun getFiles(folderId: String?): List<StoreItem> {
		BoxConfig.CLIENT_ID = Box.CLIENT_ID
		BoxConfig.CLIENT_SECRET = Box.CLIENT_SECRET

		return if (authorizerFactory.create(StorageType.BOX).isSuccess()) {
			try {
				val folderItems = BoxApiFolder(BoxSession(context)).getItemsRequest(
					folderId ?: BoxConstants.ROOT_FOLDER_ID
				).setFields(SIZE, NAME, PARENT).send()

				folderItems.map { boxItem ->
					storeItemConverter.run {
						boxItem.toStoreItem()
					}
				}.toList()
			} catch (e: BoxException) {
				listOf()
			}
		} else {
			listOf()
		}
	}
}
