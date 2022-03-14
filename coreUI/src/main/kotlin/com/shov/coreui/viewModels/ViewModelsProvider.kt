package com.shov.coreui.viewModels

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel


@Composable
inline fun <reified VM : ViewModel> singletonViewModel(): VM {
	val context = LocalContext.current as ComponentActivity

	return hiltViewModel(context)
}