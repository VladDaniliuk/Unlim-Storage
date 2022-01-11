package com.shov.unlimstorage.models.repositories.files

import android.content.Context
import com.box.androidsdk.content.BoxApiFile
import com.box.androidsdk.content.BoxApiFolder
import com.box.androidsdk.content.BoxConstants
import com.box.androidsdk.content.BoxException
import com.box.androidsdk.content.models.BoxSession
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.utils.converters.StoreMetadataConverter
import com.shov.unlimstorage.utils.converters.toStoreItem
import com.shov.unlimstorage.values.getBoxFields
import com.shov.unlimstorage.values.setItemFields
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class BoxFiles @Inject constructor(
	@ApplicationContext val context: Context,
	private val authorizerFactory: AuthorizerFactory,
	private val storeMetadataConverter: StoreMetadataConverter
) : FilesInteractor {
	private val checkAuth: Boolean
		get() = authorizerFactory.create(StorageType.BOX).isSuccess()

	override fun createFolder(folderId: String?, folderName: String) = try {
		if (checkAuth) {
			BoxApiFolder(BoxSession(context)).getCreateRequest(
				folderId ?: BoxConstants.ROOT_FOLDER_ID,
				folderName
			).send()

			true
		} else false
	} catch (e: BoxException) {
		false
	}

	override fun downloadFile(
		id: String,
		name: String,
		size: Long,
		setPercents: (Float, String) -> Unit
	) {
		if (checkAuth) {
			File(File("/storage/emulated/0/Download"), name).let { file ->
				when {
					file.createNewFile() -> {
						val boxApiFile = BoxApiFile(BoxSession(context))
							.getDownloadRequest(FileOutputStream(file), id)
							.setProgressListener { numBytes, _ ->
								setPercents((numBytes.toFloat() / size.toFloat()), name)
							}
						boxApiFile.send()
					}
					file.exists() -> {
						//TODO "FILE WAS DOWNLOADED EARLY"
					}
					else -> {
						//TODO ERROR DOWNLOAD
					}
				}
			}
		}
	}

	override fun getFileMetadata(id: String, type: ItemType) = if (checkAuth) {
		storeMetadataConverter.run {
			(when (type) {
				ItemType.FILE -> BoxApiFile(BoxSession(context)).getInfoRequest(id)
					.setFields(*getBoxFields()).send()
				ItemType.FOLDER -> BoxApiFolder(BoxSession(context)).getInfoRequest(id)
					.setFields(*getBoxFields()).send()
			}).toStoreMetadata()
		}
	} else null

	override fun getFiles(folderId: String?) = if (checkAuth) {
		try {
			BoxApiFolder(BoxSession(context))
				.getItemsRequest(folderId ?: BoxConstants.ROOT_FOLDER_ID)
				.setItemFields().send()
				.map { boxItem ->
					boxItem.toStoreItem(folderId)
				}.toList()
		} catch (e: BoxException) {
			emptyList()
		}
	} else emptyList()

	/**Upload file*/
	/*
	* try {
                    String uploadFileName = "box_logo.png";
                    InputStream uploadStream = getResources().getAssets().open(uploadFileName);
                    String destinationFolderId = "0";
                    String uploadName = "BoxSDKUpload.png";
                    BoxRequestsFile.UploadFile request = mFileApi.getUploadRequest(uploadStream, uploadName, destinationFolderId);
                    final BoxFile uploadFileInfo = request.send();
                    showToast("Uploaded " + uploadFileInfo.getName());
                    loadRootFolder();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BoxException e) {
                    e.printStackTrace();
                    BoxError error = e.getAsBoxError();
                    if (error != null && error.getStatus() == HttpURLConnection.HTTP_CONFLICT) {
                        ArrayList<BoxEntity> conflicts = error.getContextInfo().getConflicts();
                        if (conflicts != null && conflicts.size() == 1 && conflicts.get(0) instanceof BoxFile) {
                            uploadNewVersion((BoxFile) conflicts.get(0));
                            return;
                        }
                    }
                    showToast("Upload failed");
                } finally {
                    mDialog.dismiss();
                }*/
}
