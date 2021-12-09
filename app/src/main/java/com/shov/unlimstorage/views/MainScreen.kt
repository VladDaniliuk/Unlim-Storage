package com.shov.unlimstorage.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.shov.unlimstorage.ui.DownloadSnackbar
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.provider.mainNavigationViewModel
import com.shov.unlimstorage.viewModels.provider.newVersionViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.provider.updateViewModel
import com.shov.unlimstorage.viewModels.settings.UpdateViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation
import com.shov.unlimstorage.views.settings.NewVersionDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen(
	context: Context = LocalContext.current,
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	scaffoldState: ScaffoldState = rememberScaffoldState(),
	sheetContent: MutableState<(@Composable ColumnScope.() -> Unit)?> =
		remember { mutableStateOf(null) },
	sheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = updateViewModel()
) {
	NewVersionDialog()

	Column(modifier = Modifier.fillMaxSize()) {
		ModalBottomSheetLayout(
			modifier = Modifier.weight(1f),
			sheetState = sheetState,
			sheetContent = {
				sheetContent.value?.invoke(this)
				Spacer(modifier = Modifier.navigationBarsWithImePadding())
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
					topBar = { MainTopBar() }
				) {
					MainNavigation(
						mainNavigationViewModel = mainNavigationViewModel(
							scaffoldState = scaffoldState,
							sheetState = sheetState
						),
						sheetContent = sheetContent
					)
				}
			}
		)

		DownloadSnackbar()
	}

	LaunchedEffect(key1 = sheetState.isVisible) {
		if (sheetState.isVisible.not()) {
			sheetContent.value = null
		}
	}
}
