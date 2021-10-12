package com.shov.unlimstorage.models.filesRepository

import android.content.Context
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.RateLimitException
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.utils.converters.StoreItemConverter
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.values.DROPBOX_ROOT_FOLDER
import com.shov.unlimstorage.values.DROPBOX_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val storeItemConverter: StoreItemConverter,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	override fun getFiles(folderId: String?): List<StoreItem> {
		val dropBoxToken: String by Preference(context, DROPBOX_TOKEN, "")

		if (dropBoxToken.isEmpty()) {
			return emptyList()
		}

		return try {
			getFiles()?.listFolder(
				folderId ?: DROPBOX_ROOT_FOLDER
			)?.entries?.map { dropBoxItem ->
				storeItemConverter.run {
					dropBoxItem.toStoreItem(parentFolder = folderId)
				}
			}?.toList() ?: emptyList()
		} catch (e: RateLimitException) {
			emptyList()
		} catch (e: IllegalArgumentException) {
			emptyList()
		}
	}

	override fun getFileMetadata(id: String, type: ItemType): StoreMetadataItem? {
		val dropBoxToken: String by Preference(context, DROPBOX_TOKEN, "")

		if (dropBoxToken.isEmpty()) {
			return null
		}

		return try {
			storeMetadataConverter.run {
				getFiles()?.getMetadata(id)?.toStoreMetadata()
			}
		} catch (e: RateLimitException) {
			null
		} catch (e: IllegalArgumentException) {
			null
		}
	}

	private fun getFiles(): DbxUserFilesRequests? {
		val dropBoxToken: String by Preference(context, DROPBOX_TOKEN, "")

		if (dropBoxToken.isEmpty()) {
			return null
		}

		return DbxClientV2(
			DbxRequestConfig.newBuilder(context.getString(R.string.app_name)).build(),
			dropBoxToken
		).files()
	}
}
