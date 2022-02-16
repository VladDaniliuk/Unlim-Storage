package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.ui.FABScaffold
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.ui.texts.TextNavigation
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.values.PADDING_FAB
import com.shov.unlimstorage.values.SIZE_FAB
import com.shov.unlimstorage.values.Screen
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.files.FilesViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewStates.FilesScreenState
import com.shov.unlimstorage.viewStates.rememberUploadNavigationState
import com.shov.unlimstorage.views.navigations.UploadNavigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilesScreen(
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	filesViewModel: FilesViewModel = hiltViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	filesScreenState: FilesScreenState,
	onBackPress: () -> Unit,
	onFolderOpen: (BackStack) -> Unit
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow()
		.collectAsState(false)

	FABScaffold(
		onClick = {
			bottomSheetViewModel.setContent {
				UploadNavigation(
					uploadNavigationState = rememberUploadNavigationState(
						folderId = filesViewModel.folderId,
						storageType = filesViewModel.storageType
					)
				)
			}

			filesScreenState.coroutineScope.launch {
				bottomSheetViewModel.sheetState.show()
			}
		}
	) {
		SwipeRefresh(
			modifier = Modifier.fillMaxSize(),
			state = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
			onRefresh = {
				if (isConnected) {
					filesViewModel.refreshFiles()
				} else {
					scaffoldViewModel.showSnackbar(
						filesScreenState.context.getString(R.string.connection_failed)
					)
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
						modifier = Modifier.align(Alignment.Center),
						stringIdArray = arrayOf(
							R.string.nothing_to_show,
							R.string.settings,
							R.string.dot
						),
						taggedStringId = R.string.settings
					) {
						filesScreenState.navHostController.navigate(Screen.Accounts.route)
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
							size = storeItem.size,
							disk = storeItem.disk,
							enabled = filesViewModel.isClickable,
							onClick = {
								when (storeItem.type) {
									ItemType.FOLDER -> {
										filesViewModel.setOpenable(false)

										onFolderOpen(
											BackStack(
												storeItem.id,
												storeItem.disk.name,
												storeItem.name
											)
										)

										filesViewModel.setOpenable(true)
									}
									ItemType.FILE -> {
										filesViewModel.setOpenable(false)

										filesScreenState.navHostController.navigate(
											Screen.FileInfo.setStoreItem(storeItem.id)
										)

										filesViewModel.setOpenable(true)
									}
								}
							},
							onOptionClick = {
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

								filesScreenState.coroutineScope.launch {
									bottomSheetViewModel.sheetState.show()
								}
							}
						)
					}

					Spacer(
						modifier = Modifier
							.navigationBarsPadding()
							.padding(bottom = SIZE_FAB + PADDING_FAB)
					)
				}
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

	LaunchedEffect(key1 = null) {
		topAppBarViewModel.setTopBar(
			filesViewModel.folderId?.let {
				Icons.Rounded.ArrowBack to onBackPress
			},
			filesScreenState.context.getString(R.string.app_name),
			Icons.Rounded.AccountCircle to {
				filesScreenState.navHostController.navigate(Screen.Settings.route)
			}
		)
	}
}
