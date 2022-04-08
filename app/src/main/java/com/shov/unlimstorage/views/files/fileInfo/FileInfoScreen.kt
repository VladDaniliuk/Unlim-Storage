package com.shov.unlimstorage.views.files.fileInfo

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.utils.checkMultiplePermissions
import com.shov.filesfeature.utils.rememberRequestMultiplePermissionsResult
import com.shov.unlimstorage.R
import com.shov.filesfeature.utils.share
import com.shov.filesfeature.viewModels.DownloadViewModel
import com.shov.filesfeature.viewModels.FileInfoViewModel
import com.shov.filesfeature.views.fileInfo.FileInfoView

@Composable
fun FileInfoScreen(
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	fileInfoViewModel: FileInfoViewModel = hiltViewModel(),
	navigateTo: (String) -> Unit,
	popBack: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

	val launcher = rememberRequestMultiplePermissionsResult(
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.WRITE_EXTERNAL_STORAGE,
		onAllowed = {
			fileInfoViewModel.downloadFile(downloadViewModel::setProgress) { id ->
				scaffold.showSnackbar(context.getString(id))
			}
		},
		onDenied = {
			scaffold.showSnackbar(context.getString(R.string.permissions_denied))
		}
	)

	FileInfoView(
		id = fileInfoViewModel.id,
		itemType = fileInfoViewModel.storeItem?.type,
		storageTypeImageId = fileInfoViewModel.storeItem?.disk?.imageId,
		fileSize = fileInfoViewModel.storeItem?.size,
		createdTime = fileInfoViewModel.storeMetadata?.createdTime,
		modifiedTime = fileInfoViewModel.storeMetadata?.modifiedTime,
		version = fileInfoViewModel.storeMetadata?.version,
		onDescriptionClick = { fileInfoViewModel.storeMetadata?.id?.let(navigateTo) },
		description = fileInfoViewModel.storeMetadata?.description,
		sharingUsers = fileInfoViewModel.storeMetadata?.sharingUsers,
		link = fileInfoViewModel.storeMetadata?.link,
		onShareLink = {
			fileInfoViewModel.storeMetadata?.link?.let { link ->
				context.share(link)
			}
		},
		onDownloadClick = {
			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
				if (context.checkMultiplePermissions(
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE
					)
				) {
					fileInfoViewModel.downloadFile(downloadViewModel::setProgress) { id ->
						scaffold.showSnackbar(context.getString(id))
					}
				} else {
					launcher.launch(
						arrayOf(
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.READ_EXTERNAL_STORAGE
						)
					)
				}
			} else {
				fileInfoViewModel.downloadFile(downloadViewModel::setProgress) { id ->
					scaffold.showSnackbar(context.getString(id))
				}
			}
		},
		onShowSnackbar = scaffold::showSnackbar
	)

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileInfoViewModel.storeMetadata == null)) {
			fileInfoViewModel.getFileMetadata()
		}
	}

	LaunchedEffect(key1 = fileInfoViewModel.storeItem?.name) {
		scaffold.setTopBar(
			Icons.Rounded.ArrowBack to popBack,
			fileInfoViewModel.storeItem?.name,
			fileInfoViewModel.staredIcon to {
				scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
			}
		)
	}
}
