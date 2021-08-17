package com.shov.unlimstorage.models.filesRepository

import android.content.Context
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.BoxConstants
import com.box.androidsdk.content.models.BoxSession
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.values.Box
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class BoxFiles @Inject constructor(@ApplicationContext val context: Context) : FilesInteractor {
	override suspend fun getFiles(folderId: String?): Array<StoreItem> {
		BoxConfig.CLIENT_ID = Box.CLIENT_ID
		BoxConfig.CLIENT_SECRET = Box.CLIENT_SECRET

		return coroutineScope {
			val folderItems = BoxApiFolder(BoxSession(context)).getItemsRequest(
				folderId ?: BoxConstants.ROOT_FOLDER_ID
			).send()

			return@coroutineScope folderItems.map { boxItem -> StoreItem(boxItem) }.toTypedArray()
		}
	}
}
