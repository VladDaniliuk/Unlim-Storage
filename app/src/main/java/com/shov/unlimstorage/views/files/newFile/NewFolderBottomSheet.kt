package com.shov.unlimstorage.views.files.newFile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.viewStates.NewFolderBottomSheetState
import com.shov.unlimstorage.viewStates.rememberNewFolderBottomSheet

@Composable
fun NewFolderBottomSheet(newFolderBottomSheetState: NewFolderBottomSheetState) {
	if (newFolderBottomSheetState.storageType.value != null) {
		Column {
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp),
				text = stringResource(R.string.create_new_folder),
				textAlign = TextAlign.Center,
				fontSize = Typography().h6.fontSize
			)

			Row {
				OutlinedTextField(
					modifier = Modifier
						.weight(1f)
						.focusRequester(newFolderBottomSheetState.focusRequester)
						.padding(vertical = 8.dp)
						.padding(start = 8.dp),
					value = newFolderBottomSheetState.text.value,
					onValueChange = { newFolderBottomSheetState.text.value = it },
					placeholder = { Text(text = stringResource(R.string.folder_name)) },
					trailingIcon = {
						if (newFolderBottomSheetState.text.value.isNotEmpty()) {
							IconButton(onClick = { newFolderBottomSheetState.text.value = "" }) {
								Icon(
									imageVector = Icons.Rounded.Close,
									contentDescription = Icons.Rounded.Close.name
								)
							}
						}
					},
					singleLine = true
				)

				Button(
					onClick = {},
					modifier = Modifier
						.padding(horizontal = 8.dp)
						.align(Alignment.CenterVertically)
				) {
					Text(text = stringResource(R.string.create))
				}
			}
		}

		LaunchedEffect(key1 = null) {
			newFolderBottomSheetState.focusRequester.requestFocus()
		}
	} else {
		ChooseDriveBottomSheet {
			newFolderBottomSheetState.storageType.value = it
		}
	}
}

@Preview
@Composable
fun NewFolderBottomSheetPreview() {
	NewFolderBottomSheet(
		newFolderBottomSheetState = rememberNewFolderBottomSheet(storageType = StorageType.BOX)
	)
}
