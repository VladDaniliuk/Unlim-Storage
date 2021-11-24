package com.shov.unlimstorage.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.views.navigations.MainNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
	/**TopBar parameters*/
	val prevRoute = remember { mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null) }
	val title = remember { mutableStateOf<String?>(null) }
	val nextRoute = remember { mutableStateOf<Pair<ImageVector, (() -> Unit)>?>(null) }

	/**BottomSheet parameters*/
	val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

	val sheetContent =
		remember { mutableStateOf<(@Composable ColumnScope.() -> Unit)?>(null) }

	val scaffoldState = rememberScaffoldState()

	ModalBottomSheetLayout(
		sheetState = sheetState,
		sheetContent = {
			sheetContent.value?.invoke(this)
			Spacer(modifier = Modifier.navigationBarsPadding())
		},
		sheetShape = MaterialTheme.shapes.large.copy(
			bottomEnd = CornerSize(0),
			bottomStart = CornerSize(0)
		),
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
						title = title.value,
						nextRoute = nextRoute.value
					)
				}
			) {
				MainNavigation(
					scaffoldState = scaffoldState,
					setTopBar = { newPrevRoute, newTitle, newNextRoute ->
						prevRoute.value = newPrevRoute
						title.value = newTitle
						nextRoute.value = newNextRoute
					},
					sheetState = sheetState,
					sheetContent = sheetContent
				)
			}
		}
	)
}
