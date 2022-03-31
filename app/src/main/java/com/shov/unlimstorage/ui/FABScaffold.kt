package com.shov.unlimstorage.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.unlimstorage.viewModels.common.BottomSheetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FABScaffold(
	bottomSheetContent: @Composable ColumnScope.() -> Unit,
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	coroutine: CoroutineScope = rememberCoroutineScope(),
	content: @Composable () -> Unit
) {
	Scaffold(
		bottomBar = { CustomBottomAppBar() },
		isFloatingActionButtonDocked = true,
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					bottomSheetViewModel.setContent(bottomSheetContent)

					coroutine.launch {
						bottomSheetViewModel.sheetState.show()
					}
				}
			) {
				Icon(
					imageVector = Icons.Rounded.Add,
					contentDescription = Icons.Rounded.Add.name,
					tint = Color.White
				)
			}
		}
	) {
		content()
	}
}
