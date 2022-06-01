package com.shov.filesfeature.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.FileOpen
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.DownloadedItem
import com.shov.coreui.ui.icons.CustomIcon
import com.shov.coreui.ui.texts.CustomText
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.viewModels.ScaffoldViewModel
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.storagerepositories.repositories.files.FileActionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun DownloadListScreen(
    downloadListViewModel: DownloadListViewModel = hiltViewModel(),
    scaffold: ScaffoldViewModel = singletonViewModel(),
    navigationViewModel: NavigationViewModel = singletonViewModel()
) {
    val list by downloadListViewModel.list.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { item ->
            val percent by downloadListViewModel.getPercent(item.downloadedId)
                .collectAsState(initial = 0f)

            DownloadedItem(
                item,
                percent,
                onClick = { downloadListViewModel.onClick(item.downloadedId) }
            ) {
                downloadListViewModel.cancelDownload(item.downloadedId)
            }
        }
    }

    LaunchedEffect(key1 = null) {
        scaffold.setTopBar(
            Icons.Rounded.ArrowBack to navigationViewModel::popBack,
            "Downloads",
            null
        )
    }
}

@Composable
fun DownloadedItem(
    downloadedItem: DownloadedItem,
    percent: Float,
    onClick: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (percent == 1f) onClick() }
    ) {
        CustomIcon(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterVertically)
                .size(48.dp),
            imageVector = Icons.Rounded.FileOpen
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            CustomText(text = downloadedItem.name)

            CustomText(text = "id: ${downloadedItem.downloadedId}")

            Row(modifier = Modifier.fillMaxWidth()) {
                if (percent != 1f) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        progress = percent
                    )
                }

                if ((percent != -1f) and (percent != 1f)) {
                    CustomIcon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(12.dp)
                            .clickable { onCancel() },
                        imageVector = Icons.Rounded.Cancel
                    )
                }
            }
        }

        CustomIcon(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterVertically)
                .size(24.dp),
            painter = painterResource(id = downloadedItem.disk.imageId),
            tint = Color.Unspecified
        )
    }
}

@HiltViewModel
class DownloadListViewModel @Inject constructor(
    private val fileActionsRepository: FileActionsRepository,
) : ViewModel() {
    val list = fileActionsRepository.getDownloadedItems()

    fun getPercent(id: Long) = fileActionsRepository.checkDownload(id)

    fun cancelDownload(id: Long) = fileActionsRepository.cancelDownload(id)

    fun onClick(id: Long) {
        fileActionsRepository.openFile(id)
    }
}