package com.shov.filesfeature.views.newObject.newFolder

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.newObject.NewFolderViewModel
import com.shov.filesfeature.views.newObject.ChooseDriveBottomSheet

@Composable
fun NewFolderBottomSheet(
	context: Context = LocalContext.current,
	newFolderViewModel: NewFolderViewModel = hiltViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	BackHandler(onBack = navigationViewModel::popBack)

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
						onCompletion = navigationViewModel::popBack,
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
