package com.shov.filesfeature.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.shov.coreui.models.TopAppBarStatus
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun SearchScreen(
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	BackHandler {
		navigationViewModel.popBack()
		topAppBarViewModel.setSearch("")
	}
	val focusManager = LocalFocusManager.current

	Box(modifier = Modifier.fillMaxSize()) {
		Text(
			text = Screen.Search.route,
			modifier = Modifier.align(Alignment.Center)
		)
	}

	LaunchedEffect(key1 = topAppBarViewModel.searchText) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to {
				navigationViewModel.popBack()
				focusManager.clearFocus()
				topAppBarViewModel.setSearch("")
			},
			null,
			if (topAppBarViewModel.searchText.isNotEmpty()) {
				Icons.Rounded.Cancel to { topAppBarViewModel.setSearch("") }
			} else null,
			TopAppBarStatus.Search
		)
	}
}
