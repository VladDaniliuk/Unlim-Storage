package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FileDescriptionScreen(
	fileDescriptionViewModel: FileDescriptionViewModel,
	filesNavController: NavController,
	topAppBarViewModel: TopAppBarViewModel,
	scaffoldState: ScaffoldState
) {
	val coroutineScope = rememberCoroutineScope()
	val isConnected by LocalContext.current.observeConnectivityAsFlow().collectAsState(false)

	topAppBarViewModel.setTopBar(
		Icons.Rounded.Close to { filesNavController.popBackStack() },
		fileDescriptionViewModel.storeItem?.name,
		Icons.Rounded.Done to {
			coroutineScope.launch {
				scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
			}
		}
	)

	TextField(
		enabled = isConnected,
		value = fileDescriptionViewModel.description ?: "",
		onValueChange = { newDescription ->
			fileDescriptionViewModel.setNewDescription(newDescription)
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

	LaunchedEffect(key1 = null) {
		fileDescriptionViewModel.getStoreItem()
	}

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileDescriptionViewModel.description.isNullOrEmpty())) {
			fileDescriptionViewModel.getDescription()
		}
	}
}
