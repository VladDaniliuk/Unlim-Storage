package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.unlimstorage.models.items.ItemType
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.ui.FABScaffold
import com.shov.unlimstorage.ui.StoreItemView
import com.shov.unlimstorage.ui.spacers.FabSpacer
import com.shov.unlimstorage.ui.themes.customTheme.CustomTheme
import com.shov.unlimstorage.values.Screen

@Composable
fun FilesView(
	swipeRefreshState: SwipeRefreshState,
	storeItems: List<StoreItem>,
	onTextNavigationClick: (String) -> Unit,
	onFabClick: () -> Unit,
	isEnabled: Boolean,
	onStoreItemClick: (StoreItem) -> Unit,
	onOptionStoreItemClick: (StoreItem) -> Unit,
	onRefresh: () -> Unit
) {
	FABScaffold(onClick = onFabClick) {
		SwipeRefresh(
			state = swipeRefreshState,
			onRefresh = onRefresh
		) {//TODO Do progress with linear progress
			if (storeItems.isEmpty()) {
				FilesEmptyView {
					onTextNavigationClick(Screen.Accounts.route)
				}
			} else {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.verticalScroll(state = rememberScrollState())
				) {
					storeItems.forEach { storeItem ->
						StoreItemView(
							name = storeItem.name,
							type = storeItem.type,
							size = storeItem.size,
							disk = storeItem.disk,
							enabled = isEnabled,
							onClick = { onStoreItemClick(storeItem) },
							onOptionClick = { onOptionStoreItemClick(storeItem) }
						)
					}

					FabSpacer()
				}
			}
		}
	}
}

@Preview
@Composable
fun FilesPreview() {
	CustomTheme {
		FilesView(
			swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
			storeItems = List(10) {
				StoreItem("", ItemType.FILE, "Name", StorageType.GOOGLE, "1 Mb")
			},
			onTextNavigationClick = {},
			onFabClick = {},
			isEnabled = true,
			onStoreItemClick = {},
			onOptionStoreItemClick = {},
			onRefresh = {}
		)
	}
}
