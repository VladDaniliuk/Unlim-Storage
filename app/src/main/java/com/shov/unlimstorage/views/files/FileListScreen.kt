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
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.NavigationChain
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.navigations.FileListViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel

@Composable
fun FileListScreen(
	context: Context = LocalContext.current,
	fileListViewModel: FileListViewModel = hiltViewModel(),
	navHostController: NavHostController,
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel()
) {
	BackHandler(
		enabled = fileListViewModel.isBackHandlerAvailable,
		onBack = fileListViewModel::dropFromBackStack
	)

	Column {
		NavigationChain(
			iconEnabled = fileListViewModel.backStacks.isNotEmpty(),
			iconOnClick = fileListViewModel::dropAllFromBackStack,
			backStacks = fileListViewModel.backStacks,
			textOnClick = fileListViewModel::dropFromBackStack
		)


		FilesScreen(
			backStack = fileListViewModel.backStacks.lastOrNull(),
			navigateToFolder = fileListViewModel::addToBackStack,
			navigateToSettings = { navHostController.navigate(Screen.Accounts.route) }
		) { id ->
			navHostController.navigate(Screen.FileInfo.setStoreItem(id))
		}
	}

	LaunchedEffect(key1 = fileListViewModel.backStacks) {
		topAppBarViewModel.setTopBar(
			if (fileListViewModel.isBackHandlerAvailable)
				Icons.Rounded.ArrowBack to fileListViewModel::dropFromBackStack
			else null,
			context.getString(R.string.app_name),
			Icons.Rounded.AccountCircle to { navHostController.navigate(Screen.Settings.route) })
	}
}
