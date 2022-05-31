package com.shov.storagerepositories.repositories.files

import android.content.Context
import android.os.Environment
import com.shov.coremodels.inheritances.DownloadManagerRequest
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.storagerepositories.repositories.factories.FilesFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface FileActionsRepository {
    suspend fun createFolder(
        folderId: String?,
        folderName: String,
        storageType: StorageType
    ): Boolean

    fun download(
        storageType: StorageType,
        id: String,
        name: String,
        parentFolder: File = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        type: ItemType,
    )

    suspend fun uploadFile(
        inputStream: InputStream,
        name: String,
        storageType: StorageType,
        folderId: String?
    )

    suspend fun renameFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String,
        name: String
    ): Boolean

    suspend fun deleteFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String
    )
}

class FileActionsRepositoryImpl @Inject constructor(
    private val filesFactory: FilesFactory,
    @ApplicationContext val context: Context
) : FileActionsRepository {
    override suspend fun createFolder(
        folderId: String?,
        folderName: String,
        storageType: StorageType
    ) = filesFactory.create(storageType).createFolder(folderId, folderName)

    override fun download(
        storageType: StorageType,
        id: String,
        name: String,
        parentFolder: File,
        type: ItemType,
    ) {
        if (type == ItemType.FOLDER) {
            downloadFolder(id, name, storageType, parentFolder).forEach { storeItem ->
                download(
                    storeItem.disk,
                    storeItem.id,
                    storeItem.name,
                    File(parentFolder, name),
                    storeItem.type
                )
            }
        } else {
            downloadFile(storageType, id, name)
        }// TODO onExist onError
    }

    private fun downloadFolder(
        id: String,
        name: String,
        storageType: StorageType,
        parentFolder: File
    ): List<StoreItem> {
        return if (File(parentFolder, name).exists().not()) {
            if (File(parentFolder, name).mkdirs()) {
                filesFactory.create(storageType).getFiles(id)
            } else emptyList()
        } else emptyList()
    }

    private fun downloadFile(
        disk: StorageType,
        id: String,
        name: String,
    ) {
        DownloadManagerRequest(context, filesFactory.create(disk).getDownloadLink(id), name)
            .addRequestHeaders(filesFactory.create(disk).getHeaders(id))
            .enqueue()
    }//TODO need mediaScanner

    override suspend fun uploadFile(
        inputStream: InputStream,
        name: String,
        storageType: StorageType,
        folderId: String?
    ) {
        filesFactory.create(storageType).uploadFile(inputStream, name, folderId)
    }

    override suspend fun renameFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String,
        name: String
    ): Boolean = filesFactory.create(storageType).renameFile(itemType, id, name)

    override suspend fun deleteFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String
    ) {
        filesFactory.create(storageType).deleteFile(itemType, id)
    }
}
