package com.shov.unlimstorage.views.files.bottomSheets

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.viewModels.SizeConverterViewModel
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
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	sizeConverter: SizeConverterViewModel = singletonViewModel(),
	storeItem: StoreItem,
	navigateToFileInfo: (id: String) -> Unit
) {
	BackHandler {
		coroutineScope.launch {
			bottomSheetViewModel.sheetState.hide()
		}
	}

	FileActionView(
		disk = storeItem.disk,
		itemType = storeItem.type,
		name = storeItem.name,
		onDownloadClick = {
			scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
		},
		onOpenInBrowserClick = {
			scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
		},
		onShareClick = {
			scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
		},
		size = sizeConverter.toBytes(storeItem.size)
	) {
		coroutineScope.launch {
			bottomSheetViewModel.sheetState.hide()
		}.invokeOnCompletion {
			navigateToFileInfo(storeItem.id)
		}
	}
}
