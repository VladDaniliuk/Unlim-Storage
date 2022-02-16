package com.shov.unlimstorage.views.files.newFile

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.buttons.ProgressButton
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.files.NewFolderViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewFolderBottomSheet(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	newFolderViewModel: NewFolderViewModel = hiltViewModel(),
) {
	val isConnected by context.observeConnectivityAsFlow()
		.collectAsState(false)

	if (newFolderViewModel.type != null) {
		Column(
			modifier = Modifier.padding(
				horizontal = 8.dp,
				vertical = 8.dp
			)
		) {
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 8.dp),
				text = stringResource(R.string.create_new_folder),
				textAlign = TextAlign.Center,
				fontSize = Typography().h6.fontSize
			)

			Row {
				OutlinedTextField(
					isError = newFolderViewModel.textError.isNotEmpty(),
					modifier = Modifier
						.padding(end = 8.dp)
						.weight(1f)
						.align(Alignment.CenterVertically)
						.focusRequester(newFolderViewModel.focusRequester),
					value = newFolderViewModel.text,
					onValueChange = newFolderViewModel::onTextChange,
					placeholder = { Text(text = stringResource(R.string.folder_name)) },
					trailingIcon = {
						if (newFolderViewModel.text.isNotEmpty()) {
							IconButton(onClick = newFolderViewModel::onTextChange) {
								Icon(
									imageVector = Icons.Rounded.Close,
									contentDescription = Icons.Rounded.Close.name
								)
							}
						}
					},
					singleLine = true
				)

				ProgressButton(
					modifier = Modifier.align(Alignment.CenterVertically),
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
					}
				)
			}

			if (newFolderViewModel.textError.isNotEmpty()) Text(
				text = newFolderViewModel.textError,
				color = MaterialTheme.colors.error,
				style = MaterialTheme.typography.caption,
			)
		}

		LaunchedEffect(key1 = null) {
			newFolderViewModel.focusRequester.requestFocus()
		}
	} else {
		ChooseDriveBottomSheet { type ->
			newFolderViewModel.type = type
		}
	}
}

@ExperimentalAnimationApi
@Preview
@Composable
fun NewFolderBottomSheetPreview() {
	NewFolderBottomSheet()
}
