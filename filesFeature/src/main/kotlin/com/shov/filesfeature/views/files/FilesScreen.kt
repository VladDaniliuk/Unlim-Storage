package com.shov.filesfeature.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.coreui.viewModels.BottomSheetViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.models.BackStack
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.utils.navigateTo
import com.shov.filesfeature.viewModels.FilesViewModel
import com.shov.filesfeature.views.FileActionsBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.shov.coremodels.R as coreModelsR

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilesScreen(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	filesViewModel: FilesViewModel = hiltViewModel(),
	navHostController: NavHostController,
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
		nestedScrollConnection = scaffold.scrollBehavior.nestedScrollConnection,
		swipeRefreshState = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
		storeItems = storeItems,
		onTextNavigationClick = navHostController::navigateTo,
		isEnabled = filesViewModel.isClickable,
		onStoreItemClick = { storeItem ->
			filesViewModel.onStoreItemClick(
				storeItem = storeItem,
				onFolderOpen = onFolderOpen,
				onFileInfoOpen = navHostController::navigateTo
			)
		},
		onOptionStoreItemClick = { storeItem ->
			bottomSheetViewModel.setContent {
				FileActionsBottomSheet(
					disk = storeItem.disk,
					name = storeItem.name,
					onNavigate = {
						navHostController.navigate(Screen.FileInfo.setStoreItem(storeItem.id))
					},
					size = storeItem.size,
					type = storeItem.type
				)
			}

			coroutineScope.launch { bottomSheetViewModel.sheetState.show() }
		}
	) {
		filesViewModel.onRefresh(isConnected) {
			scaffold.showSnackbar(context.getString(R.string.connection_failed))
		}
	}

	LaunchedEffect(key1 = isConnected) { filesViewModel.onRefresh(isConnected) {} }

	LaunchedEffect(key1 = null) {
		scaffold.setTopBar(
			filesViewModel.folderId?.let { Icons.Rounded.ArrowBack to onBackPress },
			context.getString(coreModelsR.string.app_name),
			Icons.Rounded.AccountCircle to { navHostController.navigate(Screen.Settings.route) }
		)
	}
}
