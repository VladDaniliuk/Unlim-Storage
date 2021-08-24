package com.shov.unlimstorage.models.filesRepository

import android.content.Context
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.RateLimitException
import com.dropbox.core.v2.DbxClientV2
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.repositories.SignInRepository
import com.shov.unlimstorage.utils.toStoreItem
import com.shov.unlimstorage.values.DROPBOX_ROOT_FOLDER
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DropBoxFiles @Inject constructor(
	private val signInRepository: SignInRepository,
	@ApplicationContext val context: Context
) : FilesInteractor {
	override fun getFiles(folderId: String?): List<StoreItem> {
		return signInRepository.getAccessToken?.let { token ->
			return@let try {
				DbxClientV2(
					DbxRequestConfig.newBuilder(context.getString(R.string.app_name)).build(),
					token
				).files().listFolder(
					folderId ?: DROPBOX_ROOT_FOLDER
				).entries.map { dropBoxItem -> dropBoxItem.toStoreItem(parentFolder = folderId) }
					.toList()
			} catch (e: RateLimitException) {
				listOf()
			} catch (e: IllegalArgumentException) {
				listOf()
			}
		} ?: listOf()
	}
}
