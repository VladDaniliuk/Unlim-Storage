package com.shov.filesfeature.ui.scaffold

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.UploadFile
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.ui.buttons.CustomFloatingActionButton
import com.shov.coreui.ui.buttons.CustomFloatingActionButtonState
import com.shov.coreui.ui.buttons.FloatingActionButtonModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FABViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FABScaffold(
    fabViewModel: FABViewModel = hiltViewModel(),
    onCreateNewFolderClick: () -> Unit,
    onUploadFile: () -> Unit,
    onDownloadListClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                modifier = Modifier.padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
                state = fabViewModel.state,
                floatingActionButtonModels = listOf(
                    FloatingActionButtonModel(
                        icon = Icons.Rounded.Folder,
                        text = stringResource(id = R.string.create_new_folder),
                    ) {
                        fabViewModel.collapse()
                        onCreateNewFolderClick()
                    },
                    FloatingActionButtonModel(
                        icon = Icons.Rounded.UploadFile,
                        text = stringResource(id = R.string.upload_file),
                    ) {
                        fabViewModel.collapse()
                        onUploadFile()
                    },
                    FloatingActionButtonModel(
                        icon = Icons.Rounded.Download,
                        text = stringResource(id = R.string.download_list),
                    ) {
                        fabViewModel.collapse()
                        onDownloadListClick()
                    }
                ),
                onClick = fabViewModel::onClick
            )
        }
    ) {
        FABBackground(
            visible = fabViewModel.state == CustomFloatingActionButtonState.Expanded,
            onBackgroundClick = fabViewModel::collapse
        ) {
            content()
        }
    }

    DisposableEffect(key1 = null) {
        onDispose(fabViewModel::collapse)
    }
}
