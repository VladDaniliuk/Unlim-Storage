package com.shov.unlimstorage.views.files

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.models.BackStack
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.views.files.FilesView
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.navigateTo
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.filesfeature.viewModels.FilesViewModel
import com.shov.unlimstorage.viewStates.FilesScreenState
import kotlinx.coroutines.launch
import com.shov.coremodels.R as coreModelsR

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilesScreen(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	filesScreenState: FilesScreenState,
	filesViewModel: FilesViewModel = hiltViewModel(),
	onBackPress: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	onFolderOpen: (BackStack) -> Unit
) {
	if (filesViewModel.folderId != null) {
		BackHandler(onBack = onBackPress)
	}

	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)
	val storeItems by filesViewModel.storeItems.collectAsState()

	FilesView(
		swipeRefreshState = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
		storeItems = storeItems,
		onTextNavigationClick = filesScreenState.navHostController::navigateTo,
		isEnabled = filesViewModel.isClickable,
		onStoreItemClick = { storeItem ->
			filesViewModel.onStoreItemClick(
				storeItem = storeItem,
				onFolderOpen = onFolderOpen,
				onFileInfoOpen = filesScreenState.navHostController::navigateTo
			)
		},
		onOptionStoreItemClick = { storeItem ->
			bottomSheetViewModel.setContent {
				FileActionsBottomSheet(
					disk = storeItem.disk,
					name = storeItem.name,
					onNavigate = {
						filesScreenState.navHostController.navigate(
							Screen.FileInfo.setStoreItem(storeItem.id)
						)
					},
					size = storeItem.size,
					type = storeItem.type
				)
			}

			filesScreenState.coroutineScope.launch { bottomSheetViewModel.sheetState.show() }
		}
	) {
		filesViewModel.onRefresh(isConnected) {
			scaffold.showSnackbar(filesScreenState.context.getString(R.string.connection_failed))
		}
	}

	LaunchedEffect(key1 = isConnected) { filesViewModel.onRefresh(isConnected) {} }

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			filesViewModel.folderId?.let { Icons.Rounded.ArrowBack to onBackPress },
			filesScreenState.context.getString(coreModelsR.string.app_name),
			Icons.Rounded.AccountCircle to {
				filesScreenState.navHostController.navigate(Screen.Settings.route)
			}
		)
	}
}
