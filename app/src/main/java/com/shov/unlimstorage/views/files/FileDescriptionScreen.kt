package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import kotlinx.coroutines.launch

@Composable
fun FileDescriptionScreen(
	fileDescriptionViewModel: FileDescriptionViewModel,
	filesNavController: NavController,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit,
	scaffoldState: ScaffoldState
) {
	val coroutineScope = rememberCoroutineScope()

	setTopBar(
		Icons.Rounded.Close to { filesNavController.popBackStack() },
		fileDescriptionViewModel.storeMetadata.name,
		Icons.Rounded.Done to {
			coroutineScope.launch {
				scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
			}
		}
	)

	TextField(
		value = fileDescriptionViewModel.storeMetadata.description ?: "",
		onValueChange = { newDescription ->
			fileDescriptionViewModel.setDescription(newDescription)
		},
		placeholder = { Text(text = stringResource(id = R.string.description)) },
		modifier = Modifier
			.fillMaxSize()
			.navigationBarsWithImePadding(),
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = MaterialTheme.colors.background,
			focusedIndicatorColor = MaterialTheme.colors.background,
			unfocusedIndicatorColor = MaterialTheme.colors.background
		)
	)
}
