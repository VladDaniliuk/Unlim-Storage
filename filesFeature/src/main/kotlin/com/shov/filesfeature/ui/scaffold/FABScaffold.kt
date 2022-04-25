package com.shov.filesfeature.ui.scaffold

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FABScaffold(
	bottomSheetContent: @Composable ColumnScope.() -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel(),
	coroutine: CoroutineScope = rememberCoroutineScope(),
	content: @Composable () -> Unit
) {
	Scaffold(
		floatingActionButton = {
			SmallFloatingActionButton(onClick = {
				scaffold.setContent(bottomSheetContent)

				coroutine.launch {
					scaffold.sheetState.show()
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
