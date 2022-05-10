package com.shov.coreui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.models.TopAppBar
import com.shov.coreui.models.TopAppBarStatus
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.TopAppBarViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel

@Composable
fun CustomTopAppBar(
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
	navigationViewModel: NavigationViewModel = singletonViewModel()
) {
	CustomTopAppBar(
		topAppBar = topAppBarViewModel.topAppBar,
		searchText = topAppBarViewModel.searchText,
		onSearchClick = { navigationViewModel.navigateTo(Screen.Search.route) },
		focusRequester = topAppBarViewModel.focusRequester,
		onSearchChange = topAppBarViewModel::setSearch
	)

	LaunchedEffect(key1 = topAppBarViewModel.topAppBar.status) {
		if (topAppBarViewModel.topAppBar.status == TopAppBarStatus.Search) {
			topAppBarViewModel.focusRequester.requestFocus()
		}
	}
}

@Composable
fun CustomTopAppBar(
	topAppBar: TopAppBar,
	searchText: String,
	onSearchClick: () -> Unit,
	focusRequester: FocusRequester,
	onSearchChange: (String) -> Unit
) {
	SearchBar(status = topAppBar.status, onClick = onSearchClick) {
		Box(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(start = 2.dp)
		) {
			AnimatedIconButton(
				imageVector = topAppBar.prevRoute?.first,
				onClick = topAppBar.prevRoute?.second ?: {},
				enabled = topAppBar.prevRoute != null
			)
		}

		SearchBarTitle(
			focusRequester = focusRequester,
			status = topAppBar.status,
			textSearch = searchText,
			title = topAppBar.title,
			onTextChange = onSearchChange
		)

		Box(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(end = 2.dp)
		) {
			AnimatedIconButton(
				imageVector = topAppBar.nextRoute?.first,
				onClick = topAppBar.nextRoute?.second ?: {},
				enabled = topAppBar.nextRoute != null
			)
		}
	}
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
	CustomTopAppBar(
		topAppBar = TopAppBar(status = TopAppBarStatus.Search),
		searchText = "",
		onSearchClick = {},
		focusRequester = FocusRequester()
	) {}
}
