package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.models.StoreItem

interface FilesInteractor {
	fun getFiles(folderId: String?): List<StoreItem>
}
