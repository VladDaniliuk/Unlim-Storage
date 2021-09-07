package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.ui.TextNavigation
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.navAccounts
import com.shov.unlimstorage.values.navFiles
import com.shov.unlimstorage.values.navSettings
import com.shov.unlimstorage.viewModels.FilesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@Composable
fun FilesScreen(
	scaffoldState: ScaffoldState,
	filesNavController: NavController,
	filesViewModel: FilesViewModel,
	folderId: String? = null,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		textId: Int?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit,
	storageType: StorageType? = null
) {
	setTopBar(
		folderId?.let { Icons.Rounded.ArrowBack to { filesNavController.popBackStack() } },
		R.string.app_name,
		Icons.Rounded.AccountCircle to { filesNavController.navigate(navSettings) }
	)

	val coroutineScope = rememberCoroutineScope()
	val isClickable = remember { mutableStateOf(true) }
	val isConnected =
		LocalContext.current.observeConnectivityAsFlow().collectAsState(initial = false)
	val isRefreshing by filesViewModel.isRefreshing.collectAsState()
	val storeItemList by filesViewModel.storeItemList.collectAsState()

	val messageFailed = stringResource(id = R.string.connection_failed)

	SwipeRefresh(
		modifier = Modifier.fillMaxSize(),
		state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
		onRefresh = {
			if (isConnected.value) {
				filesViewModel.refreshFiles(
					folderId = folderId,
					storageType = storageType
				)
			} else {
				coroutineScope.launch {
					scaffoldState.snackbarHostState.showSnackbar(message = messageFailed)
				}
			}
		}
	) {
		if (storeItemList.isEmpty()) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(state = rememberScrollState())
			) {
				TextNavigation(
					stringIdArray = arrayOf(
						R.string.nothing_to_show,
						R.string.settings,
						R.string.dot
					),
					taggedStringId = R.string.settings,
					modifier = Modifier.align(Alignment.Center)
				) {
					filesNavController.navigate(navAccounts)
				}
			}
		} else {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(state = rememberScrollState())
			) {
				storeItemList.forEach { storeItem ->
					StoreItem(
						storeItem = storeItem,
						enabled = isClickable.value
					) {
						isClickable.value = false

						filesNavController.navigate(
							navFiles(
								folderId = storeItem.id,
								storageType = storeItem.disk.name
							)
						)
					}
				}

				Spacer(modifier = Modifier.navigationBarsPadding())
			}
		}
	}

	LaunchedEffect(key1 = isConnected.value) {
		if (isConnected.value) {
			filesViewModel.getFiles(
				folderId = folderId,
				storageType = storageType
			)
		} else {
			filesViewModel.checkLocalFiles(folderId = folderId)
		}
	}
}
