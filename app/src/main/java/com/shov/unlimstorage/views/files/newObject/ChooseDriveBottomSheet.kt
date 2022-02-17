package com.shov.unlimstorage.views.files.newObject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coremodels.StorageType
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.buttons.CustomIconButton
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewModels.settings.AccountsViewModel

@Composable
fun ChooseDriveBottomSheet(
	accountsViewModel: AccountsViewModel = singletonViewModel(),
	onClick: (StorageType) -> Unit
) {
	ChooseDriveBottomSheet(
		storageList = accountsViewModel.checkAllAccess(),
		onClick = onClick
	)
}

@Composable
fun ChooseDriveBottomSheet(storageList: List<StorageType>, onClick: (StorageType) -> Unit) {
	Column {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp),
			text = stringResource(R.string.new_, stringResource(id = R.string.folder)),
			textAlign = TextAlign.Center,
			fontSize = Typography().h6.fontSize
		)

		Row {
			storageList.forEach {
				CustomIconButton(
					painter = painterResource(id = it.imageId),
					text = stringResource(id = it.nameId),
					onClick = { onClick(it) }
				)
			}
		}
	}
}

@Preview
@Composable
fun ChooseDriveBottomSheetPreview() {
	ChooseDriveBottomSheet(
		storageList = listOf(StorageType.DROPBOX, StorageType.BOX),
		onClick = {}
	)
}
