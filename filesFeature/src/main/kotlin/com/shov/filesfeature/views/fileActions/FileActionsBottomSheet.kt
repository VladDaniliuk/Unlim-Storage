package com.shov.filesfeature.views.fileActions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FileActionsViewModel

@Composable
fun FileActionsBottomSheet(
	fileActionsViewModel: FileActionsViewModel = hiltViewModel(),
	context: Context = LocalContext.current,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	val storeItem by fileActionsViewModel.storeItem

	FileActionsView(
		disk = storeItem.disk,
		name = storeItem.name,
		onDoesntWork = {
			scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
		},
		size = storeItem.size,
		type = storeItem.type,
	) {
		navigationViewModel.navigateTo(Screen.FileInfo.setStoreItem(storeItem.id))
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
