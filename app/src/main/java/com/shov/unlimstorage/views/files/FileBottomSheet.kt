package com.shov.unlimstorage.views.files

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shov.unlimstorage.R

@Composable
fun FilesBottomSheet() {
	Column {
		Text(text = stringResource(id = R.string.app_name))
	}
}
