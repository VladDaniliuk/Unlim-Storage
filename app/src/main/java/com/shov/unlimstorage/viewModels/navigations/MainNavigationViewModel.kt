package com.shov.unlimstorage.viewModels.navigations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shov.unlimstorage.models.MainNavigationViewModelFactory
import com.shov.unlimstorage.values.UNCHECKED_CAST
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MainNavigationViewModel @OptIn(ExperimentalMaterialApi::class) @AssistedInject constructor(
	@Assisted private val _scaffoldState: ScaffoldState,
	@Assisted private val _sheetState: ModalBottomSheetState
) : ViewModel() {
	val scaffoldState get() = _scaffoldState

	@OptIn(ExperimentalMaterialApi::class)
	val sheetState = _sheetState

	@Suppress(UNCHECKED_CAST)
	companion object {
		@OptIn(ExperimentalMaterialApi::class)
		fun provideFactory(
			assistedFactory: MainNavigationViewModelFactory,
			scaffoldState: ScaffoldState,
			sheetState: ModalBottomSheetState
		): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
			override fun <T : ViewModel?> create(modelClass: Class<T>): T {
				return assistedFactory.createMainNavigationViewModel(
					scaffoldState,
					sheetState
				) as T
			}
		}
	}
}
