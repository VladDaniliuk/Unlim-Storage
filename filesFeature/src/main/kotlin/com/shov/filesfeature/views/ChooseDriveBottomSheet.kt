package com.shov.filesfeature.views

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coremodels.models.StorageType
import com.shov.coreui.ui.buttons.CustomIconButton
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.newObject.ChooseDriveViewModel

@Composable
fun ChooseDriveBottomSheet(
	chooseDriveViewModel: ChooseDriveViewModel = hiltViewModel(),
	onClick: (StorageType) -> Unit
) {
	ChooseDriveBottomSheet(
		storageList = chooseDriveViewModel.checkAccess(),
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