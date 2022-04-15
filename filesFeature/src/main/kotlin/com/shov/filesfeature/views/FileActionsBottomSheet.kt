package com.shov.filesfeature.views

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coreui.viewModels.BottomSheetViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
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
	id: String,
	onNavigate: (String) -> Unit,
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
			onNavigate(Screen.FileInfo.setStoreItem(id))
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
