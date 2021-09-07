package com.shov.unlimstorage.views

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.views.files.FilesBottomSheet
import com.shov.unlimstorage.views.navigations.MainNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
	//TopBar parameters
	val prevRoute = remember { mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null) }
	val textId = remember { mutableStateOf<Int?>(null) }
	val nextRoute = remember { mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null) }

	val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
	val scaffoldState = rememberScaffoldState()

	ModalBottomSheetLayout(
		sheetState = sheetState,
		sheetContent = {
			FilesBottomSheet()
		},
		content = {
			Scaffold(
				scaffoldState = scaffoldState,
				snackbarHost = { snackBarHostState ->
					SnackbarHost(
						hostState = snackBarHostState,
						modifier = Modifier.navigationBarsPadding()
					) { snackBarData ->
						Snackbar(snackbarData = snackBarData)
					}
				},
				topBar = {
					MainTopBar(
						prevRoute = prevRoute.value,
						textId = textId.value,
						nextRoute = nextRoute.value
					)
				}
			) {
				MainNavigation(
					scaffoldState = scaffoldState,
					setTopBar = { newPrevRoute, newTextId, newNextRoute ->
						prevRoute.value = newPrevRoute
						textId.value = newTextId
						nextRoute.value = newNextRoute
					}
				)
			}
		}
	)
}
