package com.shov.unlimstorage.models.filesRepository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.StoreItem
import com.shov.unlimstorage.utils.toStoreItem
import com.shov.unlimstorage.values.GOOGLE_FIELDS
import com.shov.unlimstorage.values.getGoogleQ
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleFiles @Inject constructor(@ApplicationContext val context: Context) : FilesInteractor {
	override suspend fun getFiles(folderId: String?): List<StoreItem>? {
		return GoogleSignIn.getLastSignedInAccount(context)?.let { googleAccount ->
			return@let Drive.Builder(
				AndroidHttp.newCompatibleTransport(),       //httpTransport
				JacksonFactory.getDefaultInstance(),
				GoogleAccountCredential.usingOAuth2(        //httpRequestInitializer
					context, listOf(DriveScopes.DRIVE)      //
				).apply {
					selectedAccount = googleAccount.account //give googleAccount exists
				}
			).setApplicationName(context.getString(R.string.app_name))
				.build()                                    //build Drive
				.Files()
				.list()                                     //get Files.List
				.apply {
					fields = GOOGLE_FIELDS                  //requests fields(id,name,etc.)
					q = getGoogleQ(folderId = folderId)     //sorting (add folder, remove trashed)
				}.execute().files.map { googleDriveItem ->
					googleDriveItem.toStoreItem()
				}.toList()
		}
	}
}
