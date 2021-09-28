package com.shov.unlimstorage.views.navigations

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.*
import com.shov.unlimstorage.views.files.FilesScreen
import com.shov.unlimstorage.views.settings.SettingsScreen
import com.shov.unlimstorage.views.settings.accounts.AccountsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun FilesNavigation(
	scaffoldState: ScaffoldState,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		textId: Int?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit
) {
	val filesNavController = rememberNavController()

	NavHost(navController = filesNavController, startDestination = navFiles()) {
		composable(navAccounts) {
			AccountsScreen(
				accountsViewModel = hiltViewModel(),
				filesNavController = filesNavController,
				setTopBar = setTopBar
			)
		}
		composable(
			arguments = listOf(
				navArgument(argFolderId) { nullable = true },
				navArgument(argStorageType) { nullable = true }
			),
			route = navFiles()
		) { navBackStackEntry ->
			navBackStackEntry.arguments?.let { arguments ->
				FilesScreen(
					scaffoldState = scaffoldState,
					filesNavController = filesNavController,
					filesViewModel = hiltViewModel(),
					folderId = arguments.getString(argFolderId),
					setTopBar = setTopBar,
					storageType = arguments.getString(argStorageType)
						?.let { storageType ->
							StorageType.valueOf(storageType)
						}
				)
			}
		}
		composable(navSettings) {
			SettingsScreen(
				filesNavController = filesNavController,
				setTopBar = setTopBar
			)
		}
	}
}
