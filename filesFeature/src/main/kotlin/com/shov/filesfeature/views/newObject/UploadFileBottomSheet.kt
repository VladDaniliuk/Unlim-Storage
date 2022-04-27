package com.shov.filesfeature.views.newObject

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.utils.getName
import com.shov.filesfeature.viewModels.newObject.UploadFileViewModel

@Composable
fun UploadFileBottomSheet(
	uploadFileViewModel: UploadFileViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	val pickFileLauncher = rememberLauncherForActivityResult(
		ActivityResultContracts.OpenDocument()
	) { fileUri: Uri? ->
		fileUri?.let { uri: Uri ->
			context.contentResolver.openFileDescriptor(uri, "r", null)
				?.let { parcelFileDescriptor: ParcelFileDescriptor ->
					uploadFileViewModel.uploadFile(
						parcelFileDescriptor,
						context.contentResolver.getName(uri),
						navigationViewModel::popBack
					)
				} ?: navigationViewModel.popBack()
		} ?: navigationViewModel.popBack()
	}

	if (uploadFileViewModel.type == null) {
		ChooseDriveBottomSheet { storageType: StorageType ->
			uploadFileViewModel.type = storageType
		}
	} else {
		LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
	}

	LaunchedEffect(key1 = uploadFileViewModel.type) {
		if (uploadFileViewModel.type != null) {
			pickFileLauncher.launch(arrayOf("*/*"))
		}
	}
}
