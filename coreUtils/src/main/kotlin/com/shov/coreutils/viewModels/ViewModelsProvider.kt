package com.shov.coreutils.viewModels

import android.app.Activity
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Method for creating a singleton [ViewModel] instance for the given [ComponentActivity] or [ContextWrapper].
 * @param VM the type of the ViewModel to create.
 * @property key the key to create different instances of [ViewModel] or use singleton instance.
 * */
@Composable
inline fun <reified VM : ViewModel> singletonViewModel(key: String? = VM::class.simpleName): VM {
	var context = LocalContext.current

	while ((context !is Activity).and(context is ContextWrapper)) {
		context = (context as ContextWrapper).baseContext
	}

	return viewModel((context as ComponentActivity), key)
}
