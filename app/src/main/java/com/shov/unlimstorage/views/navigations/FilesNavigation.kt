package com.shov.unlimstorage.views.navigations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.*
import com.shov.unlimstorage.viewModels.fileDescriptionViewModel
import com.shov.unlimstorage.viewModels.fileInfoViewModel
import com.shov.unlimstorage.views.files.FileDescriptionScreen
import com.shov.unlimstorage.views.files.FileInfoScreen
import com.shov.unlimstorage.views.files.FilesScreen
import com.shov.unlimstorage.views.settings.SettingsScreen
import com.shov.unlimstorage.views.settings.accounts.AccountsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun FilesNavigation(
	scaffoldState: ScaffoldState,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit,
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>,
	sheetState: ModalBottomSheetState
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
		composable(navFileInfo) {
			filesNavController.previousBackStackEntry
				?.arguments
				?.getParcelable<StoreItem>(argStoreItem)
				?.let { item ->
					FileInfoScreen(
						fileInfoViewModel = fileInfoViewModel(item),
						filesNavController = filesNavController,
						scaffoldState = scaffoldState,
						setTopBar = setTopBar
					)
				}
		}

		composable(navFileDescription) {
			filesNavController.previousBackStackEntry
				?.arguments
				?.getParcelable<StoreMetadataItem>(argStoreMetadata)
				?.let { storeMetadata ->
					FileDescriptionScreen(
						filesNavController = filesNavController,
						fileDescriptionViewModel = fileDescriptionViewModel(storeMetadata),
						setTopBar = setTopBar,
						scaffoldState = scaffoldState
					)
				}
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
						},
					sheetContent = sheetContent,
					sheetState = sheetState
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
