package com.shov.filesfeature.views.files

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shov.coremodels.models.ItemType
import com.shov.coremodels.models.StorageType
import com.shov.coremodels.models.StoreItem
import com.shov.coreui.ui.LinearPullRefreshIndicator
import com.shov.coreutils.values.Screen
import com.shov.filesfeature.ui.StoreItemView
import com.shov.filesfeature.values.PADDING_FAB
import com.shov.filesfeature.values.SIZE_FAB

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesView(
	swipeRefreshState: SwipeRefreshState,
	storeItems: List<StoreItem>,
	onTextNavigationClick: (String) -> Unit,
	isEnabled: Boolean,
	onStoreItemClick: (StoreItem) -> Unit,
	onOptionStoreItemClick: (id: String) -> Unit,
	onRefresh: () -> Unit
) {
	SwipeRefresh(
		modifier = Modifier.navigationBarsPadding(),
		state = swipeRefreshState,
		onRefresh = onRefresh,
		indicator = { state: SwipeRefreshState, refreshTrigger: Dp ->
			LinearPullRefreshIndicator(state, refreshTrigger)
		}
	) {
		if (storeItems.isEmpty()) {
			FilesEmptyView {
				onTextNavigationClick(Screen.Accounts.route)
			}
		} else {
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(
					items = storeItems,
					key = StoreItem::id
				) { storeItem ->
					StoreItemView(
						modifier = Modifier.animateItemPlacement(),
						name = storeItem.name,
						type = storeItem.type,
						size = storeItem.size,
						disk = storeItem.disk,
						enabled = isEnabled,
						onClick = { onStoreItemClick(storeItem) },
						onOptionClick = { onOptionStoreItemClick(storeItem.id) },
						onLongClick = { onOptionStoreItemClick(storeItem.id) }
					)
				}

				item {
					Spacer(modifier = Modifier.height(SIZE_FAB + PADDING_FAB))
				}
			}
		}
	}
}

@Preview
@Composable
private fun FilesPreview() {
	FilesView(
		swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
		storeItems = List(10) {
			StoreItem("", ItemType.FILE, "Name", StorageType.GOOGLE, "1 Mb")
		},
		onTextNavigationClick = {},
		isEnabled = true,
		onStoreItemClick = {},
		onOptionStoreItemClick = {},
		onRefresh = {}
	)
}
