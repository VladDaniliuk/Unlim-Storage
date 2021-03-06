package com.shov.boxstorage

import android.content.Context
import com.box.androidsdk.content.BoxApiFile
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.BoxApiSearch
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.BoxConstants
import com.box.androidsdk.content.BoxException
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.boxstorage.converters.getFileMetadata
import com.shov.boxstorage.converters.toStoreItem
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StoreItem
import com.shov.storage.FilesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class BoxFilesDataSource @Inject constructor(
    @ApplicationContext val context: Context
) : FilesDataSource {
    private val checkAuth: Boolean
        get() {
            BoxConfig.CLIENT_ID = BOX_CLIENT_ID
            BoxConfig.CLIENT_SECRET = BOX_CLIENT_SECRET

            return BoxSession(context).user?.status == BoxUser.Status.ACTIVE
        }

    override fun createFolder(folderId: String?, folderName: String) = if (checkAuth) {
        try {
            BoxApiFolder(BoxSession(context)).getCreateRequest(
                folderId ?: BoxConstants.ROOT_FOLDER_ID,
                folderName
            ).send()

            true
        } catch (e: BoxException) {
            false
        }
    } else false

    override fun getDownloadLink(id: String): String = "https://api.box.com/2.0/files/$id/content"

    override fun getHeaders(id: String): List<Pair<String, String>> =
        listOf("Authorization" to "Bearer ${BoxSession(context).authInfo.accessToken()}")

    override fun getFileMetadata(id: String, type: ItemType) = if (checkAuth) {
        when (type) {
            ItemType.FILE -> BoxApiFile(BoxSession(context))
            ItemType.FOLDER -> BoxApiFolder(BoxSession(context))
        }.getFileMetadata(id, type)
    } else null

    override fun getFiles(folderId: String?) = if (checkAuth) {
        try {
            BoxApiFolder(BoxSession(context))
                .getItemsRequest(folderId ?: BoxConstants.ROOT_FOLDER_ID)
                .setFields("size", "name", "parent").send()
                .filter { (it.type == "file") or (it.type == "folder") }
                .map { boxItem ->
                    boxItem.toStoreItem(folderId) {
                        context.getString(second, first)
                    }
                }.toList()
        } catch (e: BoxException) {
            emptyList()
        }
    } else emptyList()

    override fun searchFiles(name: String): List<StoreItem> = if (checkAuth) {
        try {
            BoxApiSearch(BoxSession(context)).getSearchRequest(name)
                .setFields("size", "name", "parent")
                .send()
                .filter { (it.type == "file") or (it.type == "folder") }
                .map { boxItem ->
                    boxItem.toStoreItem(null) {
                        context.getString(second, first)
                    }
                }.toList()
        } catch (e: BoxException) {
            emptyList()
        }
    } else emptyList()

    override suspend fun uploadFile(inputStream: InputStream, name: String, folderId: String?) {
        BoxApiFile(BoxSession(context)).getUploadRequest(
            inputStream,
            name,
            folderId ?: BoxConstants.ROOT_FOLDER_ID
        ).send()
    }

    override suspend fun renameFile(itemType: ItemType, id: String, name: String) = if (checkAuth) {
        try {
            if (itemType == ItemType.FILE) {
                BoxApiFile(BoxSession(context)).getRenameRequest(id, name).send()
            } else {
                BoxApiFolder(BoxSession(context)).getRenameRequest(id, name).send()
            }
            true
        } catch (e: BoxException) {
            false
        }
    } else false

    override suspend fun deleteFile(itemType: ItemType, id: String) {
        if (checkAuth) {
            if (itemType == ItemType.FILE) {
                BoxApiFile(BoxSession(context)).getDeleteRequest(id).send()
            } else {
                BoxApiFolder(BoxSession(context)).getDeleteRequest(id).send()
            }
        }
    }

    override suspend fun shareFile(itemType: ItemType, id: String): String = if (checkAuth) {
        if (itemType == ItemType.FILE) {
            BoxApiFile(BoxSession(context)).getCreateSharedLinkRequest(id).send()
                .sharedLink
                .url
        } else {
            BoxApiFolder(BoxSession(context)).getCreateSharedLinkRequest(id).send()
                .sharedLink
                .url
        }
    } else throw IllegalStateException("Box user not authorized")

    override suspend fun changeDescription(id: String, description: String) {
        BoxApiFile(BoxSession(context)).getUpdateRequest(id).apply {
            this.description = description
            this.send()
        }
    }
}
