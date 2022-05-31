package com.shov.storage

import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StoreItem
import com.shov.coremodels.models.StoreMetadataItem
import java.io.InputStream

interface FilesDataSource {
    fun createFolder(folderId: String?, folderName: String): Boolean
    fun getDownloadLink(id: String): String
    fun getHeaders(id: String): List<Pair<String, String>>
    fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem?
    fun getFiles(folderId: String? = null): List<StoreItem>
    suspend fun uploadFile(inputStream: InputStream, name: String, folderId: String?)
    suspend fun renameFile(itemType: ItemType, id: String, name: String): Boolean
}
