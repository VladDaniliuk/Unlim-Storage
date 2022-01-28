package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.TextNavigation

@Composable
fun FilesEmptyView(onTextClick: () -> Unit) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(state = rememberScrollState())
	) {
		TextNavigation(
			modifier = Modifier.align(Alignment.Center),
			stringIdArray = arrayOf(
				R.string.nothing_to_show,
				R.string.settings,
				R.string.dot
			),
			taggedStringId = R.string.settings,
			onClick = onTextClick
		)
	}
}

@Preview
@Composable
fun FilesEmptyPreview() {
	FilesEmptyView {}
}