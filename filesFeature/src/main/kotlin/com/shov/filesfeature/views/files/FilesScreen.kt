package com.shov.filesfeature.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.models.BackStack
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FilesViewModel

@Composable
fun FilesScreen(
	context: Context = LocalContext.current,
	filesViewModel: FilesViewModel = hiltViewModel(),
	onBackPress: () -> Unit,
	navigationViewModel: NavigationViewModel = singletonViewModel(),
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
		onTextNavigationClick = navigationViewModel::navigateTo,
		isEnabled = filesViewModel.isClickable,
		onStoreItemClick = { storeItem ->
			filesViewModel.onStoreItemClick(
				storeItem = storeItem,
				onFolderOpen = onFolderOpen,
				onFileInfoOpen = navigationViewModel::navigateTo
			)
		},
		onOptionStoreItemClick = { id ->
			navigationViewModel.navigateTo(BottomSheet.FileAction.setStoreItemId(id))
		}
	) {
		filesViewModel.onRefresh(isConnected) {
			scaffold.showSnackbar(context.getString(R.string.connection_failed))
		}
	}

	LaunchedEffect(key1 = isConnected) { filesViewModel.onRefresh(isConnected) {} }
}
