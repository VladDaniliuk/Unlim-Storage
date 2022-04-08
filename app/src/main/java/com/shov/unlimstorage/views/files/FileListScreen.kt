package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shov.filesfeature.ui.NavigationChain
import com.shov.filesfeature.ui.scaffold.FABScaffold
import com.shov.filesfeature.viewModels.FileListViewModel
import com.shov.filesfeature.views.FileListNavigation
import com.shov.filesfeature.views.newObject.NewObjectBottomSheetNavigation

@Composable
fun FileListScreen(
	fileListViewModel: FileListViewModel = hiltViewModel(),
	navHostController: NavHostController,
	filesNavHostController: NavHostController = rememberNavController()
) {
	Column {
		NavigationChain(
			iconEnabled = fileListViewModel.backStacks.isNotEmpty(),
			iconOnClick = {
				fileListViewModel.dropAllFromBackStack(filesNavHostController)
			},
			backStacks = fileListViewModel.backStacks
		) { index ->
			fileListViewModel.dropFromBackStack(filesNavHostController, index)
		}

		FABScaffold(
			bottomSheetContent = {
				NewObjectBottomSheetNavigation(
					folderId = fileListViewModel.backStacks.lastOrNull()?.folderId,
					storageType = fileListViewModel.backStacks.lastOrNull()?.storageType
				)
			}
		) {
			FileListNavigation(
				filesNavHostController = filesNavHostController,
				navHostController = navHostController
			) {
				fileListViewModel.dropFromBackStack(filesNavHostController)
			}
		}
	}

	LaunchedEffect(key1 = filesNavHostController.currentBackStackEntryAsState().value) {
		fileListViewModel.compareBackStack(filesNavHostController)
	}
}
