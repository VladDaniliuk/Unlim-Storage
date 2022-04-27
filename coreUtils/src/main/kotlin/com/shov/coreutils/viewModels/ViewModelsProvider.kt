package com.shov.coreutils.viewModels

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Method for creating a singleton [ViewModel] instance for the given [ComponentActivity].
 * @param VM the type of the ViewModel to create.
 * @property key the key to create different instances of [ViewModel] or use singleton instance.
 * */
@Composable
inline fun <reified VM : ViewModel> singletonViewModel(key: String? = VM::class.simpleName): VM {
	val context = LocalContext.current as ComponentActivity

	return viewModel(context, key)
}
