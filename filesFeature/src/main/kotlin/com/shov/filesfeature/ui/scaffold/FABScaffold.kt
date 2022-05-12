package com.shov.filesfeature.ui.scaffold

import android.content.Context
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.ui.LocalHostState
import com.shov.coreui.ui.buttons.CustomFloatingActionButton
import com.shov.coreui.ui.buttons.CustomFloatingActionButtonState
import com.shov.coreui.ui.buttons.FloatingActionButtonModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FABViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FABScaffold(
	modifier: Modifier = Modifier,
	context: Context = LocalContext.current,
	fabViewModel: FABViewModel = hiltViewModel(),
	onCreateNewFolderClick: () -> Unit,
	onUploadFile: () -> Unit,
	coroutine: CoroutineScope = rememberCoroutineScope(),
	snackbar: SnackbarHostState = LocalHostState.current,
	content: @Composable () -> Unit
) {
	Scaffold(
		modifier = modifier,
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

						coroutine.launch {
							snackbar.showSnackbar(context.getString(R.string.list_unavailable))
						}
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
