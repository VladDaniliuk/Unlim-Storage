package com.shov.unlimstorage.viewModels.provider

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.di.ViewModelFactoryProvider
import com.shov.unlimstorage.viewModels.settings.NewVersionViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
private fun getFactory() = EntryPointAccessors.fromActivity(
	LocalContext.current as Activity,
	ViewModelFactoryProvider::class.java
)

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
