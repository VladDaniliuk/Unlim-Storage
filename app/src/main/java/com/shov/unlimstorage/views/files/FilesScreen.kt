package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.ui.TextNavigation
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.SizeConverterViewModel
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.files.FilesViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilesScreen(
	onShowSheet: suspend CoroutineScope.(isShow: Boolean) -> Unit,
	scaffoldState: ScaffoldState,
	filesNavController: NavController,
	filesViewModel: FilesViewModel = hiltViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?>,
	sizeConverter: SizeConverterViewModel = singletonViewModel()
) {
	topAppBarViewModel.setTopBar(
		filesViewModel.folderId?.let { Icons.Rounded.ArrowBack to { filesNavController.popBackStack() } },
		stringResource(R.string.app_name),
		Icons.Rounded.AccountCircle to { filesNavController.navigate(Screen.Settings.route) }
	)

	val coroutineScope = rememberCoroutineScope()
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)
	val messageFailed = stringResource(id = R.string.connection_failed)

	SwipeRefresh(
		modifier = Modifier.fillMaxSize(),
		state = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
		onRefresh = {
			if (isConnected) {
				filesViewModel.refreshFiles()
			} else {
				coroutineScope.launch {
					scaffoldState.snackbarHostState.showSnackbar(messageFailed)
				}
			}
		}/*,
		indicator = { _, _ ->
			LinearProgressIndicator()
		}*///TODO Do progress with linear progress
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
					filesNavController.navigate(Screen.Accounts.route)
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
						name = storeItem.name,
						type = storeItem.type,
						size = sizeConverter.toBytes(storeItem.size),
						disk = storeItem.disk,
						enabled = filesViewModel.isClickable,
						onClick = {
							when (storeItem.type) {
								ItemType.FOLDER -> {
									filesViewModel.setOpenable(false)

									filesNavController.navigate(
										Screen.Files.openFolder(
											storeItem.id,
											storeItem.disk.name
										)
									)

									filesViewModel.setOpenable(true)
								}
								ItemType.FILE -> {
									filesViewModel.setOpenable(false)

									filesNavController.navigate(
										Screen.FileInfo.setStoreItem(storeItem.id)
									)

									filesViewModel.setOpenable(true)
								}
							}
						},
						onOptionClick = {
							sheetContent.value = {
								FileActionsBottomSheet(
									disk = storeItem.disk,
									name = storeItem.name,
									onDontWork = {
										scaffoldState.snackbarHostState
											.showSnackbar("Doesn't work now")
									},
									onNavigate = {
										filesNavController.navigate(
											Screen.FileInfo.setStoreItem(storeItem.id)
										)
									},
									onShowSheet = onShowSheet,
									size = sizeConverter.toBytes(storeItem.size),
									type = storeItem.type
								)
							}

							coroutineScope.launch {
								onShowSheet(true)
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
			filesViewModel.getFiles()
		} else {
			filesViewModel.checkLocalFiles()
		}
	}
}
