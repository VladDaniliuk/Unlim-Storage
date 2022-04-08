package com.shov.unlimstorage.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.views.FileActionsView
import com.shov.unlimstorage.R
import com.shov.coreui.viewModels.BottomSheetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FileActionsBottomSheet(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	disk: StorageType,
	name: String,
	onNavigate: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	size: String?,
	type: ItemType
) {
	BackHandler {
		coroutineScope.launch {
			bottomSheetViewModel.sheetState.hide()
		}
	}

	FileActionsView(
		disk = disk,
		name = name,
		onDoesntWork = {
			scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
		},
		size = size,
		type = type,
	) {
		coroutineScope.launch {
			bottomSheetViewModel.sheetState.hide()
		}.invokeOnCompletion {
			onNavigate()
		}
	}
}

@Preview
@Composable
fun FileActionsPreview() {
	FileActionsView(
		disk = StorageType.GOOGLE,
		name = "File",
		onDoesntWork = {},
		size = "12 MB",
		type = ItemType.FILE,
	) {}
}
