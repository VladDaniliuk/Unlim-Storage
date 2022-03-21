package com.shov.unlimstorage.views.files.fileInfo

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.utils.observeConnectivityAsFlow
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.context.share
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import com.shov.unlimstorage.views.Permission
import com.shov.unlimstorage.views.files.fileInfo.fileInfoViews.FileInfoView

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

	if (fileInfoViewModel.isDialogShown) {
		Permission(
			onDismissRequest = { fileInfoViewModel.setShowDialog(false) },
			onHasAccess = {
				fileInfoViewModel.downloadFile(
					setPercents = downloadViewModel::setProgress,
					onStart = {
						scaffold.showSnackbar(context.getString(R.string.download_started))
					}
				) {
					scaffold.showSnackbar(context.getString(R.string.download_error))
				}
			}
		)
	}

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
		onDownloadClick = fileInfoViewModel::setShowDialog,
		onShowSnackbar = scaffold::showSnackbar
	)

	LaunchedEffect(key1 = null) {
		if (isConnected) {
			fileInfoViewModel.getFileMetadata()
		}
	}

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
