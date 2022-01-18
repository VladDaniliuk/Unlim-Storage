package com.shov.unlimstorage.views.files

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.ui.NavigationChain
import com.shov.unlimstorage.viewModels.navigations.FileListViewModel
import com.shov.unlimstorage.views.navigations.FileListNavigation

@Composable
fun FileListScreen(
	fileListViewModel: FileListViewModel = hiltViewModel(),
	navHostController: NavHostController,
	filesNavHostController: NavHostController = rememberNavController()
) {
	if (fileListViewModel.isBackHandlerAvailable) {
		BackHandler {
			fileListViewModel.dropFromBackStack(filesNavHostController)
		}
	}

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

		FileListNavigation(
			filesNavHostController = filesNavHostController,
			navHostController = navHostController
		) {
			fileListViewModel.dropFromBackStack(filesNavHostController)
		}
	}

	LaunchedEffect(key1 = filesNavHostController.currentBackStackEntryAsState().value) {
		fileListViewModel.compareBackStack(filesNavHostController)
	}
}
