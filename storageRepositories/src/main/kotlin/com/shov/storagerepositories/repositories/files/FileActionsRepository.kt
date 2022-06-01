package com.shov.storagerepositories.repositories.files

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.net.toFile
import com.shov.coremodels.inheritances.DownloadManagerRequest
import com.shov.coremodels.models.DownloadedItem
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.localstorage.StoreItemDataSource
import com.shov.storagerepositories.repositories.factories.FilesFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface FileActionsRepository {
    suspend fun createFolder(
        folderId: String?,
        folderName: String,
        storageType: StorageType
    ): Boolean

    suspend fun download(
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

    suspend fun shareFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String
    ): String

    fun getDownloadedItems(): Flow<List<DownloadedItem>>

    fun checkDownload(id: Long): Flow<Float>

    fun cancelDownload(id: Long)

    fun openFile(id: Long)
}

class FileActionsRepositoryImpl @Inject constructor(
    private val filesFactory: FilesFactory,
    private val storeItemDataSource: StoreItemDataSource,
    @ApplicationContext val context: Context
) : FileActionsRepository {
    override suspend fun createFolder(
        folderId: String?,
        folderName: String,
        storageType: StorageType
    ) = filesFactory.create(storageType).createFolder(folderId, folderName)

    override suspend fun download(
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

    private suspend fun downloadFile(
        disk: StorageType,
        id: String,
        name: String,
    ) {
        val x = DownloadManagerRequest(context, filesFactory.create(disk).getDownloadLink(id), name)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(name)
            .addRequestHeaders(filesFactory.create(disk).getHeaders(id))
            .enqueue()

        storeItemDataSource.addDownloadedFile(DownloadedItem(x, id, name, disk))
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

    override suspend fun shareFile(
        itemType: ItemType,
        storageType: StorageType,
        id: String
    ): String = filesFactory.create(storageType).shareFile(itemType, id)

    override fun getDownloadedItems(): Flow<List<DownloadedItem>> =
        storeItemDataSource.getDownloadedFiles()

    override fun checkDownload(id: Long): Flow<Float> = flow {
        context.getSystemService(DownloadManager::class.java)
            ?.query(DownloadManager.Query().setFilterById(id))
            ?.let {
                while (it.moveToFirst()) {
                    delay(100)

                    when (it.getIntOrNull(it.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        DownloadManager.STATUS_SUCCESSFUL -> emit(1f)
                        DownloadManager.STATUS_FAILED -> emit(-1f)
                        DownloadManager.STATUS_RUNNING -> {
                            emit(
                                (it.getFloatOrNull(it.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                    ?: 0f) / (it.getFloatOrNull(it.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                                    ?: 0f)
                            )
                        }
                        DownloadManager.STATUS_PAUSED -> emit(-1f)
                        DownloadManager.STATUS_PENDING -> emit(-1f)
                        else -> emit(-1f)
                    }
                }
            }
    }

    override fun cancelDownload(id: Long) {
        context.getSystemService(DownloadManager::class.java)?.remove(id)
    }

    override fun openFile(id: Long) {
        context.getSystemService(DownloadManager::class.java)
            ?.query(DownloadManager.Query().setFilterById(id))?.let { cursor ->
                if (cursor.moveToFirst()) {
                    if (cursor.getIntOrNull(
                            cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        ) == DownloadManager.STATUS_SUCCESSFUL
                    ) {
                        context.openFile(cursor.getFile())
                    }
                }
            }

    }
}

fun Cursor.getFile(): File = Uri
    .parse(getString(getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))).toFile()

fun Context.openFile(file: File) {
    val install = Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        data = FileProvider.getUriForFile(this@openFile, "$packageName.provider", file)
    }

    startActivity(install)
}