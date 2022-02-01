package com.shov.unlimstorage.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.NavigationChain
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.navigations.FileListViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.views.navigations.FileListNavigation

@Composable
fun FileListScreen(
	context: Context = LocalContext.current,
	fileListViewModel: FileListViewModel = hiltViewModel(),
	navHostController: NavHostController,
	filesNavHostController: NavHostController = rememberNavController(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel()
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

		FileListNavigation(filesNavHostController = filesNavHostController)
	}

	LaunchedEffect(key1 = filesNavHostController.currentBackStackEntryAsState().value) {
		fileListViewModel.compareBackStack(filesNavHostController)
	}

	LaunchedEffect(key1 = fileListViewModel.backStacks) {
		topAppBarViewModel.setTopBar(
			if (fileListViewModel.backStacks.isNotEmpty())
				Icons.Rounded.ArrowBack to { filesNavHostController.popBackStack() }
			else null,
			context.getString(R.string.app_name),
			Icons.Rounded.AccountCircle to { navHostController.navigate(Screen.Settings.route) }
		)
	}
}
