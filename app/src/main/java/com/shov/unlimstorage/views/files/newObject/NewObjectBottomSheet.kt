package com.shov.unlimstorage.views.files.newObject

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.buttons.CustomIconButton
import com.shov.coreui.viewModels.singletonViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewObjectBottomSheet(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	onFolderCreateClick: () -> Unit,
	onUploadClick: (Uri?) -> Unit
) {
	BackHandler {
		coroutineScope.launch {
			@OptIn(ExperimentalMaterialApi::class)
			bottomSheetViewModel.sheetState.hide()
		}
	}

	val pickPictureLauncher =
		rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument(), onUploadClick)

	Column {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp),
			text = stringResource(R.string.new_, stringResource(R.string.`object`)),
			textAlign = TextAlign.Center,
			fontSize = Typography().h6.fontSize
		)

		Row {
			CustomIconButton(
				imageVector = Icons.Rounded.Folder,
				text = stringResource(R.string.folder),
				onClick = onFolderCreateClick
			)

			CustomIconButton(
				imageVector = Icons.Rounded.Upload,
				text = stringResource(R.string.upload)
			) {
				pickPictureLauncher.launch(arrayOf("*/*"))
			}
		}
	}
}
