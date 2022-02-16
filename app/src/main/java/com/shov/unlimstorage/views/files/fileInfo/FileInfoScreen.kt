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
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.utils.context.share
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.views.Permission
import com.shov.unlimstorage.views.files.fileInfo.fileInfoViews.FileInfoView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FileInfoScreen(
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	fileInfoViewModel: FileInfoViewModel = hiltViewModel(),
	navigateTo: (String) -> Unit,
	popBack: () -> Unit,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

	if (fileInfoViewModel.isDialogShown) {
		Permission(
			onDismissRequest = { fileInfoViewModel.setShowDialog(false) },
			onHasAccess = { fileInfoViewModel.downloadFile(downloadViewModel::setProgress) }
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
		onShowSnackbar = scaffoldViewModel::showSnackbar
	)

	LaunchedEffect(key1 = null) {
		launch(Dispatchers.IO) {
			fileInfoViewModel.getStoreItem()
		}.invokeOnCompletion {
			if (isConnected) {
				fileInfoViewModel.getFileMetadata()
			}
		}
	}

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileInfoViewModel.storeMetadata == null)) {
			fileInfoViewModel.getFileMetadata()
		}
	}

	LaunchedEffect(key1 = fileInfoViewModel.storeItem?.name) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to popBack,
			fileInfoViewModel.storeItem?.name,
			fileInfoViewModel.staredIcon to {
				scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
			}
		)
	}
}