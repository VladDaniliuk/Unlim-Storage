package com.shov.unlimstorage.views.files

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.CustomIconButton
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.values.argStoreItem
import com.shov.unlimstorage.values.navFileInfo
import com.shov.unlimstorage.viewModels.files.FileActionsViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun FileActionsBottomSheet(
	fileActionViewModel: FileActionsViewModel,
	filesNavController: NavController,
	scaffoldState: ScaffoldState,
	sheetState: ModalBottomSheetState
) {
	val coroutineScope = rememberCoroutineScope()

	Column {
		StoreItem(
			storeItem = fileActionViewModel.storeItem,
			enabled = false,
			isDividerVisible = false,
			isOptionVisible = false
		)

		Divider()

		LazyRow(modifier = Modifier.padding(vertical = 12.dp)) {
			items(
				items = listOf<Triple<ImageVector, Int, () -> Unit>>(
					Triple(Icons.Rounded.Download, R.string.download) {
						coroutineScope.launch {
							scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
						}
					},
					Triple(Icons.Rounded.OpenInBrowser, R.string.open_in_browser) {
						coroutineScope.launch {
							scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
						}
					},
					Triple(Icons.Rounded.Share, R.string.share_link) {
						coroutineScope.launch {
							scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
						}
					},
					Triple(Icons.Rounded.Info, R.string.show_info) {
						coroutineScope.launch { sheetState.hide() }

						filesNavController.currentBackStackEntry
							?.arguments
							?.putParcelable(argStoreItem, fileActionViewModel.storeItem)
						filesNavController.navigate(navFileInfo)
					}
				)
			) { item ->
				CustomIconButton(
					image = item.first,
					text = stringResource(id = item.second),
					onClick = item.third
				)
			}
		}

		Divider()
	}
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun FileActionsPreview() {
	FileActionsBottomSheet(
		fileActionViewModel = hiltViewModel(),
		filesNavController = rememberNavController(),
		scaffoldState = rememberScaffoldState(),
		sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded),
	)
}
