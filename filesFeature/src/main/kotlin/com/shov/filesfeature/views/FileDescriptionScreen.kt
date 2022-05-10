package com.shov.filesfeature.views

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.utils.observeConnectivityAsFlow
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.R
import com.shov.filesfeature.viewModels.FileDescriptionViewModel

@Composable
fun FileDescriptionScreen(
	navigationViewModel: NavigationViewModel = singletonViewModel(),
	context: Context = LocalContext.current,
	fileDescriptionViewModel: FileDescriptionViewModel = hiltViewModel(),
	scaffold: ScaffoldViewModel = singletonViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel()
) {
	val isConnected by context.observeConnectivityAsFlow().collectAsState(false)

	TextField(
		modifier = Modifier
			.fillMaxSize()
			.navigationBarsPadding()
			.imePadding(),
		enabled = isConnected,
		value = fileDescriptionViewModel.description ?: "",
		onValueChange = { newDescription ->
			fileDescriptionViewModel.setNewDescription(newDescription)
		},
		placeholder = { Text(text = stringResource(id = R.string.description)) },
		colors = TextFieldDefaults.textFieldColors(
			containerColor = MaterialTheme.colorScheme.background,
			focusedIndicatorColor = MaterialTheme.colorScheme.background,
			unfocusedIndicatorColor = MaterialTheme.colorScheme.background
		)
	)

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileDescriptionViewModel.description.isNullOrEmpty())) {
			fileDescriptionViewModel.getDescription()
		}
	}

	LaunchedEffect(key1 = fileDescriptionViewModel.storeItem?.name) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.Close to navigationViewModel::popBack,
			fileDescriptionViewModel.storeItem?.name,
			Icons.Rounded.Done to {
				scaffold.showSnackbar(context.getString(R.string.doesnt_work_now))
			}
		)
	}
}
