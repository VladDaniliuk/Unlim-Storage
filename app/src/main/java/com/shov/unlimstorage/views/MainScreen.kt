package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.ui.DownloadSnackbar
import com.shov.unlimstorage.ui.MainTopBar
import com.shov.unlimstorage.utils.launchWhenStarted
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.*
import com.shov.unlimstorage.views.navigations.MainNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen(
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	updateViewModel: UpdateViewModel = updateViewModel(),
	downloadViewModel: DownloadViewModel = singletonViewModel()
) {
	val context = LocalContext.current
	val currentLifecycleOwner = LocalLifecycleOwner.current

	/**States*/
	val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
	val scaffoldState = rememberScaffoldState()

	/**BottomSheet content*/
	val sheetContent = remember { mutableStateOf<(@Composable ColumnScope.() -> Unit)?>(null) }

	if (updateViewModel.isDialogShown) {
		updateViewModel.lastRelease?.let { lastRelease ->
			NewVersionDialog(
				updateViewModel = updateViewModel,
				newVersionViewModel = newVersionViewModel(lastReleaseItem = lastRelease),
				onDismissRequest = updateViewModel::hideDialog,
				downloadViewModel = singletonViewModel()
			)
		}
	}

	Column(modifier = Modifier.fillMaxSize()) {
		ModalBottomSheetLayout(
			modifier = Modifier.weight(1f),
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
							prevRoute = topAppBarViewModel.prevRoute,
							title = topAppBarViewModel.title,
							nextRoute = topAppBarViewModel.nextRoute
						)
					}
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

		when (downloadViewModel.percents) {
			in 0.01f..0.99f -> {
				DownloadSnackbar(
					percents = downloadViewModel.percents,
					onDismissRequest = downloadViewModel::dismissDownloading,
					title = downloadViewModel.title
				)
			}
			else -> {}
		}
	}

	LaunchedEffect(key1 = null) {
		context.observeConnectivityAsFlow().onEach { isConnected ->
			if (updateViewModel.isShowAgain.and(isConnected)) updateViewModel.checkAppVersion()
		}.launchWhenStarted(currentLifecycleOwner.lifecycleScope)
	}
}
