package com.shov.unlimstorage.views.files

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.BackStack
import com.shov.unlimstorage.ui.FABScaffold
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.utils.context.observeConnectivityAsFlow
import com.shov.unlimstorage.values.PADDING_FAB
import com.shov.unlimstorage.values.SIZE_FAB
import com.shov.unlimstorage.viewModels.SizeConverterViewModel
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.files.FilesViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewStates.rememberUploadNavigationState
import com.shov.unlimstorage.views.files.bottomSheets.FileActionsBottomSheet
import com.shov.unlimstorage.views.navigations.UploadNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilesScreen(
	backStack: BackStack?,
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutine: CoroutineScope = rememberCoroutineScope(),
	filesViewModel: FilesViewModel = hiltViewModel(),
	navigateToFolder: (BackStack) -> Unit,
	navigateToSettings: () -> Unit,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	sizeConverter: SizeConverterViewModel = singletonViewModel(),
	navigateToFileInfo: (id: String) -> Unit,
) {
	val isConnected by LocalContext.current.observeConnectivityAsFlow()
		.collectAsState(false)

	FABScaffold(
		onClick = {
			bottomSheetViewModel.setContent {
				UploadNavigation(
					uploadNavigationState = rememberUploadNavigationState(
						folderId = backStack?.folderId,
						storageType = backStack?.storageType
					)
				)
			}

			coroutine.launch {
				bottomSheetViewModel.sheetState.show()
			}
		}
	) {
		SwipeRefresh(
			modifier = Modifier.fillMaxSize(),
			state = rememberSwipeRefreshState(isRefreshing = filesViewModel.isRefreshing),
			onRefresh = {
				filesViewModel.onRefresh(
					isConnected,
					backStack?.folderId,
					backStack?.storageType
				) {
					scaffoldViewModel.showSnackbar(context.getString(R.string.connection_failed))
				}
			}//TODO Do progress with linear progress
		) {
			if (filesViewModel.storeItemList.isEmpty()) {
				FilesEmptyView(navigateToSettings)
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
								filesViewModel.navigate(
									id = storeItem.id,
									itemType = storeItem.type,
									storageType = storeItem.disk,
									navigateToFileInfo = navigateToFileInfo,
									navigateToFolder = navigateToFolder,
									folderName = storeItem.name
								)
							},
							onOptionClick = {
								bottomSheetViewModel.setContent {
									FileActionsBottomSheet(
										storeItem = storeItem,
										navigateToFileInfo = navigateToFileInfo
									)
								}

								coroutine.launch {
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

	LaunchedEffect(key1 = isConnected, key2 = backStack) {
		filesViewModel.onConnectionChange(isConnected, backStack)
	}
}
