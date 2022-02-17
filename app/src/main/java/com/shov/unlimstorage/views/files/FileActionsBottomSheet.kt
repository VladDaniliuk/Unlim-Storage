package com.shov.unlimstorage.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.R
import com.shov.coremodels.ItemType
import com.shov.coremodels.StorageType
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
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
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
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
			scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
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
