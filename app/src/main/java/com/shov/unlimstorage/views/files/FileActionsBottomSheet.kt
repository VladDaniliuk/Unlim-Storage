package com.shov.unlimstorage.views.files

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.unlimstorage.R
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.CustomIconButton
import com.shov.unlimstorage.ui.StoreItem
import com.shov.unlimstorage.viewModels.BottomSheetViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FileActionsBottomSheet(
	bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	disk: StorageType,
	name: String,
	onNavigate: () -> Unit,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	size: String?,
	type: ItemType
) {
	BackHandler {
		coroutineScope.launch {
			bottomSheetViewModel.sheetState.hide()
		}
	}

	Column {
		StoreItem(
			disk = disk,
			enabled = false,
			isDividerVisible = false,
			isOptionVisible = false,
			name = name,
			size = size,
			type = type
		)

		Divider()

		Row(modifier = Modifier.padding(vertical = 12.dp)) {
			CustomIconButton(
				imageVector = Icons.Rounded.Download,
				text = stringResource(id = R.string.download)
			) {
				scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
			}

			CustomIconButton(
				imageVector = Icons.Rounded.OpenInBrowser,
				text = stringResource(id = R.string.open_in_browser)
			) {
				scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
			}

			CustomIconButton(
				imageVector = Icons.Rounded.Share,
				text = stringResource(id = R.string.share_link)
			) {
				scaffoldViewModel.showSnackbar(context.getString(R.string.doesnt_work_now))
			}

			CustomIconButton(
				imageVector = Icons.Rounded.Info,
				text = stringResource(id = R.string.show_info),
				onClick = {
					coroutineScope.launch {
						bottomSheetViewModel.sheetState.hide()
					}.invokeOnCompletion {
						onNavigate.invoke()
					}
				}
			)
		}

		Divider()
	}
}

@Preview
@Composable
fun FileActionsPreview() {
	FileActionsBottomSheet(
		disk = StorageType.GOOGLE,
		name = "File",
		onNavigate = {},
		size = "12 MB",
		type = ItemType.FILE
	)
}
