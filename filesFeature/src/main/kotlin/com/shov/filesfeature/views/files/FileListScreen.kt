package com.shov.filesfeature.views.files

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shov.coreutils.values.BottomSheet
import com.shov.filesfeature.ui.NavigationChain
import com.shov.filesfeature.ui.scaffold.FABScaffold
import com.shov.filesfeature.viewModels.FileListViewModel
import com.shov.filesfeature.views.FileListNavigation

@Composable
fun FileListScreen(
	fileListViewModel: FileListViewModel = hiltViewModel(),
	navHostController: NavHostController,
	filesNavHostController: NavHostController = rememberNavController()
) {
	FABScaffold(
		onCreateNewFolderClick = {
			navHostController.navigate(
				BottomSheet.NewFolder.setParent(
					fileListViewModel.backStacks.lastOrNull()?.storageType,
					fileListViewModel.backStacks.lastOrNull()?.folderId
				)
			)
		},
		onUploadFile = {
			navHostController.navigate(
				BottomSheet.UploadFile.setParent(
					fileListViewModel.backStacks.lastOrNull()?.storageType,
					fileListViewModel.backStacks.lastOrNull()?.folderId
				)
			)
		}
	) {
		Column {
			NavigationChain(
				backStacks = fileListViewModel.backStacks,
				iconEnabled = fileListViewModel.backStacks.isNotEmpty(),
				iconOnClick = {
					fileListViewModel.dropAllFromBackStack(filesNavHostController)
				},
			) { index ->
				fileListViewModel.dropFromBackStack(filesNavHostController, index)
			}

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
