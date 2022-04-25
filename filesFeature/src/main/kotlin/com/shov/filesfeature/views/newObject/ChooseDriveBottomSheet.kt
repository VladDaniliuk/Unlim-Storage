package com.shov.filesfeature.views.newObject

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
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
import com.shov.coreui.ui.texts.CustomText
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
	Surface(
		modifier = Modifier
			.navigationBarsPadding()
			.padding(bottom = 4.dp)
	) {
		Column {
			CustomText(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
				text = stringResource(R.string.choose_drive),
				textAlign = TextAlign.Center,
				textStyle = Typography().titleLarge
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
}

@Preview
@Composable
fun ChooseDriveBottomSheetPreview() {
	ChooseDriveBottomSheet(
		storageList = listOf(StorageType.DROPBOX, StorageType.BOX),
		onClick = {}
	)
}
