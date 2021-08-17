package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.models.StoreItem
import javax.inject.Inject

class DropBoxFiles @Inject constructor(): FilesInteractor {
	override suspend fun getFiles(folderId: String?): Array<StoreItem> {
		TODO("Not yet implemented")
	}
}
