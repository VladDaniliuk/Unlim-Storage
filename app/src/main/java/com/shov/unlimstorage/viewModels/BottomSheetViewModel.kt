package com.shov.unlimstorage.viewModels

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor() : ViewModel() {
	var sheetContent by mutableStateOf<(@Composable ColumnScope.() -> Unit)?>(null)

	@OptIn(ExperimentalMaterialApi::class)
	val sheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)

	fun setContent(sheetContent: (@Composable ColumnScope.() -> Unit)? = null) {
		this.sheetContent = sheetContent
	}
}