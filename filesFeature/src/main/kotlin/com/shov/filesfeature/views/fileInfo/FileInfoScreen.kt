package com.shov.filesfeature.views.fileInfo

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
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.utils.checkMultiplePermissions
import com.shov.filesfeature.utils.rememberRequestMultiplePermissionsResult
import com.shov.filesfeature.viewModels.FileInfoViewModel
import com.shov.filesfeature.views.fileInfo.views.FileInfoView

@Composable
fun FileInfoScreen(
    navigationViewModel: NavigationViewModel = singletonViewModel(),
    context: Context = LocalContext.current,
    fileInfoViewModel: FileInfoViewModel = hiltViewModel(),
    scaffold: ScaffoldViewModel = singletonViewModel(),
) {
    val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

    val launcher = rememberRequestMultiplePermissionsResult(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        onAllowed = {
            fileInfoViewModel.downloadFile { id ->
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
        onDescriptionClick = {
            fileInfoViewModel.storeMetadata?.id?.let { id ->
                navigationViewModel.navigateTo(Screen.FileDescription.setStoreItemId(id))
            }
        },
        description = fileInfoViewModel.storeMetadata?.description,
        sharingUsers = fileInfoViewModel.storeMetadata?.sharingUsers,
        link = fileInfoViewModel.shareLink,
        onShareLink = fileInfoViewModel::shareFile,
        onDownloadClick = {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                if (context.checkMultiplePermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    fileInfoViewModel.downloadFile { id ->
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
                fileInfoViewModel.downloadFile { id ->
                    scaffold.showSnackbar(context.getString(id))
                }
            }
        },
        onShowSnackbar = scaffold::showSnackbar,
        onRenameClick = {
            navigationViewModel.navigateTo(
                BottomSheet.RenameFile.setStoreItemId(
                    fileInfoViewModel.storeItem!!.disk.name,
                    fileInfoViewModel.storeItem!!.id,
                    fileInfoViewModel.storeItem!!.name,
                    fileInfoViewModel.storeItem!!.type.name
                )
            )
        },
        onDeleteClick = {
            if (isConnected)
                fileInfoViewModel.deleteFile {
                    navigationViewModel.popBack()
                }
        }
    )

    LaunchedEffect(key1 = isConnected) {
        if (isConnected.and(fileInfoViewModel.storeMetadata == null)) {
            fileInfoViewModel.getFileMetadata()
        }
    }

    LaunchedEffect(key1 = fileInfoViewModel.storeItem?.name) {
        scaffold.setTopBar(
            Icons.Rounded.ArrowBack to navigationViewModel::popBack,
            fileInfoViewModel.storeItem?.name,
            fileInfoViewModel.staredIcon to {
                scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
            }
        )
    }
}
