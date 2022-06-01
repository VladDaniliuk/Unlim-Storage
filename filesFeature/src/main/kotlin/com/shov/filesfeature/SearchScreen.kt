package com.shov.filesfeature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StoreItem
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.ui.StoreItemView
import com.shov.storagerepositories.repositories.files.FilesInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun SearchScreen(
    scaffold: ScaffoldViewModel = singletonViewModel(),
    navigationViewModel: NavigationViewModel = singletonViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    SearchView(
        value = searchViewModel.value,
        onValueChange = searchViewModel::onValueChange,
        list = searchViewModel.searchList,
        onClick = { id ->
            navigationViewModel.navigateTo(Screen.FileInfo.setStoreItem(id))
        },
        onSearchClick = {
            if (searchViewModel.value.isEmpty()) {
                scaffold.showSnackbar("Text for search is empty")
            } else searchViewModel.onSearchClick()
        }
    )

    LaunchedEffect(key1 = null) {
        scaffold.setTopBar(
            Icons.Rounded.ArrowBack to navigationViewModel::popBack,
            "Search",
            null
        )
    }
}

@Composable
fun SearchView(
    value: String,
    onValueChange: (String) -> Unit,
    list: List<StoreItem>,
    onSearchClick: () -> Unit,
    onClick: (id: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                value = value,
                onValueChange = onValueChange,
                maxLines = 1,
                singleLine = true
            )

            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = onSearchClick
            ) {
                Text(text = "Search")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) { item ->
                StoreItemView(
                    name = item.name,
                    type = item.type,
                    size = item.size,
                    disk = item.disk,
                    onClick = { onClick(item.id) },
                    isOptionVisible = false
                )
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val filesInfoRepository: FilesInfoRepository) :
    ViewModel() {
    var value by mutableStateOf("")
        private set

    var searchList by mutableStateOf(emptyList<StoreItem>())
        private set

    fun onValueChange(value: String) {
        this.value = value
    }

    fun onSearchClick() {
        viewModelScope.launch(Dispatchers.Default) {
            searchList = filesInfoRepository.search(value)
        }
    }
}
