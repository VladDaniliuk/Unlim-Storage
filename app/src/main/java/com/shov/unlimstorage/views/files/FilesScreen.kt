package com.shov.unlimstorage.views.files

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
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
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.ui.TextNavigation
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.*
import com.shov.unlimstorage.viewModels.files.FilesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun FilesScreen(
	scaffoldState: ScaffoldState,
	filesNavController: NavController,
	filesViewModel: FilesViewModel,
	folderId: String? = null,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit,
	storageType: StorageType? = null,
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>,
	sheetState: ModalBottomSheetState
) {
	setTopBar(
		folderId?.let { Icons.Rounded.ArrowBack to { filesNavController.popBackStack() } },
		stringResource(R.string.app_name),
		Icons.Rounded.AccountCircle to { filesNavController.navigate(navSettings) }
	)

	val coroutineScope = rememberCoroutineScope()
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)
	val messageFailed = stringResource(id = R.string.connection_failed)

	SwipeRefresh(
		modifier = Modifier.fillMaxSize(),
		state = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
		onRefresh = {
			if (isConnected) {
				filesViewModel.refreshFiles(folderId, storageType)
			} else {
				coroutineScope.launch {
					scaffoldState.snackbarHostState.showSnackbar(messageFailed)
				}
			}
		}
	) {
		if (filesViewModel.storeItemList.isEmpty()) {
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
				filesViewModel.storeItemList.forEach { storeItem ->
					StoreItem(
						storeItem = storeItem,
						enabled = filesViewModel.isClickable,
						onClick = {
							when (storeItem.type) {
								ItemType.FOLDER -> {
									filesViewModel.setClickable(false)

									filesNavController.navigate(
										navFiles(
											folderId = storeItem.id,
											storageType = storeItem.disk.name
										)
									)

									filesViewModel.setClickable(true)
								}
								ItemType.FILE -> {
									filesViewModel.setClickable(false)

									filesNavController.currentBackStackEntry
										?.arguments
										?.putParcelable(argStoreItem, storeItem)
									filesNavController.navigate(navFileInfo)

									filesViewModel.setClickable(true)
								}
							}
						},
						onOptionClick = {
							sheetContent.value = {
								FileActionsBottomSheet(
									filesNavController = filesNavController,
									scaffoldState = scaffoldState,
									sheetState = sheetState,
									storeItem = storeItem
								)
							}

							coroutineScope.launch {
								sheetState.show()
							}
						}
					)
				}

				Spacer(modifier = Modifier.navigationBarsPadding())
			}
		}
	}

	LaunchedEffect(key1 = isConnected) {
		if (isConnected) {
			filesViewModel.getFiles(
				folderId = folderId,
				storageType = storageType
			)
		} else {
			filesViewModel.checkLocalFiles(folderId = folderId)
		}
	}
}