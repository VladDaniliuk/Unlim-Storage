package com.shov.unlimstorage.views.files.newObject.newFolder

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.views.NewFolderView
import com.shov.unlimstorage.R
import com.shov.coreui.viewModels.BottomSheetViewModel
import com.shov.filesfeature.viewModels.newObject.NewFolderViewModel
import com.shov.filesfeature.views.ChooseDriveBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewFolderBottomSheet(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	newFolderViewModel: NewFolderViewModel = hiltViewModel(),
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

	if (newFolderViewModel.type != null) {
		NewFolderView(
			focusRequester = newFolderViewModel.focusRequester,
			value = newFolderViewModel.text,
			onValueChange = newFolderViewModel::onTextChange,
			onTrailingIconClick = newFolderViewModel::onTextChange,
			onClick = { onError ->
				if (isConnected) {
					newFolderViewModel.createFolder(
						onCompletion = {
							coroutineScope.launch {
								@OptIn(ExperimentalMaterialApi::class)
								bottomSheetViewModel.sheetState.hide()
							}
						},
						textError = context.getString(R.string.check_name),
						onError = onError
					)
				} else {
					newFolderViewModel.textError =
						context.getString(R.string.check_connection)
					onError()
				}
			},
			textError = newFolderViewModel.textError
		)

		LaunchedEffect(key1 = null) {
			newFolderViewModel.focusRequester.requestFocus()
		}
	} else {
		ChooseDriveBottomSheet { type ->
			newFolderViewModel.type = type
		}
	}
}
