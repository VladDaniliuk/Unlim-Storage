package com.shov.filesfeature.views.newObject.newFile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.viewModels.newObject.UploadFileViewModel
import com.shov.filesfeature.views.newObject.ChooseDriveBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.InputStream

@Composable
fun UploadBottomSheet(
	file: InputStream?,
	uploadFileViewModel: UploadFileViewModel = hiltViewModel(),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	scaffold: ScaffoldViewModel = singletonViewModel()
) {
	if (uploadFileViewModel.type == null) ChooseDriveBottomSheet { storageType ->
		uploadFileViewModel.type = storageType
	} else LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

	LaunchedEffect(key1 = uploadFileViewModel.type) {
		if (uploadFileViewModel.type != null)
			uploadFileViewModel.uploadFile(file) {
				coroutineScope.launch {
					@OptIn(ExperimentalMaterialApi::class)
					scaffold.sheetState.hide()
				}
			}
	}
}
