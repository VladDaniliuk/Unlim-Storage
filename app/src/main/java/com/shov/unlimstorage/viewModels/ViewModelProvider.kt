package com.shov.unlimstorage.viewModels

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.di.ViewModelFactoryProvider
import com.shov.unlimstorage.models.items.StoreItem
import com.shov.unlimstorage.models.items.StoreMetadataItem
import com.shov.unlimstorage.models.preferences.Preference
import com.shov.unlimstorage.values.IS_UPDATE_SHOW
import com.shov.unlimstorage.viewModels.files.FileDescriptionViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
private fun getFactory() = EntryPointAccessors.fromActivity(
	LocalContext.current as Activity,
	ViewModelFactoryProvider::class.java
)

@Composable
fun fileDescriptionViewModel(storeMetadataItem: StoreMetadataItem): FileDescriptionViewModel {
	return viewModel(
		factory = FileDescriptionViewModel.provideFactory(
			getFactory().fileDescriptionViewModelFactory(),
			storeMetadataItem
		)
	)
}

@Composable
fun fileInfoViewModel(storeItem: StoreItem): FileInfoViewModel {
	return viewModel(
		factory = FileInfoViewModel.provideFactory(
			getFactory().fileInfoViewModelFactory(),
			storeItem
		)
	)
}

@ExperimentalMaterialApi
@Composable
fun mainNavigationViewModel(
	scaffoldState: ScaffoldState,
	sheetState: ModalBottomSheetState
): MainNavigationViewModel {
	return viewModel(
		factory = MainNavigationViewModel.provideFactory(
			getFactory().mainNavigationViewModelFactory(),
			scaffoldState,
			sheetState
		)
	)
}

@Composable
fun newVersionViewModel(lastReleaseItem: LastReleaseItem): NewVersionViewModel {
	return viewModel(
		factory = NewVersionViewModel.provideFactory(
			getFactory().newVersionViewModelFactory(),
			lastReleaseItem
		)
	)
}

@Composable
inline fun <reified VM : ViewModel> singletonViewModel(): VM {
	val context = LocalContext.current as ComponentActivity

	return hiltViewModel(context)
}

@Composable
fun updateViewModel(): UpdateViewModel {
	val context = LocalContext.current as AppCompatActivity
	val isShowAgain = Preference(context, IS_UPDATE_SHOW, true)

	return viewModel(
		context,
		factory = UpdateViewModel.provideFactory(
			getFactory().updateViewModelFactory(),
			isShowAgain
		)
	)
}
