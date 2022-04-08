package com.shov.filesfeature.views

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FileDescriptionViewModel

@Composable
fun FileDescriptionScreen(
	context: Context = LocalContext.current,
	fileDescriptionViewModel: FileDescriptionViewModel = hiltViewModel(),
	onCloseClick: () -> Unit,
	scaffold: ScaffoldViewModel = singletonViewModel()
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

	TextField(
		enabled = isConnected,
		value = fileDescriptionViewModel.description ?: "",
		onValueChange = { newDescription ->
			fileDescriptionViewModel.setNewDescription(newDescription)
		},
		placeholder = { Text(text = stringResource(id = R.string.description)) },
		modifier = Modifier
			.fillMaxSize()
			.navigationBarsPadding()
			.imePadding(),
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = MaterialTheme.colors.background,
			focusedIndicatorColor = MaterialTheme.colors.background,
			unfocusedIndicatorColor = MaterialTheme.colors.background
		)
	)

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileDescriptionViewModel.description.isNullOrEmpty())) {
			fileDescriptionViewModel.getDescription()
		}
	}

	LaunchedEffect(key1 = fileDescriptionViewModel.storeItem?.name) {
		scaffold.setTopBar(
			Icons.Rounded.Close to onCloseClick,
			fileDescriptionViewModel.storeItem?.name,
			Icons.Rounded.Done to {
				scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
			}
		)
	}
}