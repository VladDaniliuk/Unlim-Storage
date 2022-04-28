package com.shov.unlimstorage.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.shov.unlimstorage.ui.CustomScaffold
import com.shov.unlimstorage.ui.DownloadSnackbar
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.views.navigations.MainNavigation
import com.shov.unlimstorage.views.settings.newVersion.NewVersionObserver

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(bottomSheetViewModel: BottomSheetViewModel = singletonViewModel()) {
	NewVersionObserver()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.navigationBarsPadding(bottom = false)
	) {
		ModalBottomSheetLayout(
			modifier = Modifier.weight(1f),
			sheetState = bottomSheetViewModel.sheetState,
			sheetContent = {
				bottomSheetViewModel.sheetContent?.invoke(this)

				Spacer(
					modifier = Modifier
						.padding(bottom = 4.dp)
						.navigationBarsWithImePadding()
				)
			},
			sheetShape = MaterialTheme.shapes.large.copy(
				bottomEnd = CornerSize(0),
				bottomStart = CornerSize(0)
			),
			content = {
				CustomScaffold {
					MainNavigation()
				}
			}
		)

		DownloadSnackbar()
	}

	LaunchedEffect(key1 = bottomSheetViewModel.sheetState.isVisible) {
		if (bottomSheetViewModel.sheetState.isVisible.not()) {
			bottomSheetViewModel.setContent()
		}
	}
}
